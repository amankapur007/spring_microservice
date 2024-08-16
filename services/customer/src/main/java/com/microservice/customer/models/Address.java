package com.microservice.customer.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class Address {
    private String street;
    private String houseNumber;
    private String zipCode;
}
