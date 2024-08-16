package com.microservice.customer.service;

import com.microservice.customer.exception.CustomerNotFoundException;
import com.microservice.customer.models.Customer;
import com.microservice.customer.models.CustomerRequest;
import com.microservice.customer.models.CustomerResponse;
import com.microservice.customer.repository.CustomerRepository;
import com.microservice.customer.utils.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    public String createCustomer(CustomerRequest customerRequest) {
        Customer customer = repository.save(mapper.toCustomer(customerRequest));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest customerRequest) {
        var customer  = repository.findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFoundException("customer not found for the provided id "+customerRequest.id()));
        merge(customer, customerRequest);
        repository.save(customer);
    }

    private void merge(Customer customer, CustomerRequest customerRequest) {
        if(StringUtils.isNotBlank(customerRequest.firstname())){
            customer.setFirstName(customerRequest.firstname());
        }
        if(StringUtils.isNotBlank(customerRequest.lastname())){
            customer.setLastName(customerRequest.lastname());
        }
        if(StringUtils.isNotBlank(customerRequest.email())){
            customer.setEmail(customerRequest.email());
        }
        if(null!=customerRequest.address()){
            customer.setAddress(customerRequest.address());
        }
    }

    public void delete(String customerId) {
        repository.deleteById(customerId);
    }

    public CustomerResponse findById(String customerId) {
        return  repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException("customer not found for the provided id "+customerId));
    }

    public List<CustomerResponse> findAll() {
        return repository.findAll()
                .stream().map(customer -> mapper.fromCustomer(customer))
                .toList();
    }

    public boolean exists(String customerId) {
        return repository.findById(customerId).isPresent();
    }
}
