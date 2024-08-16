package com.microservice.customer.utils;

import com.microservice.customer.models.Customer;
import com.microservice.customer.models.CustomerRequest;
import com.microservice.customer.models.CustomerResponse;

public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request){
        if(request == null){
            return null;
        }
        return Customer.builder()
                .firstName(request.firstname())
                .lastName(request.lastname())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public CustomerResponse fromCustomer(Customer customer){
        return new CustomerResponse(customer.getId(), customer.getFirstName(),
                customer.getLastName(), customer.getEmail(), customer.getAddress());
    }
}
