package com.example.pinkbullmakeup.Service;

import com.example.pinkbullmakeup.Entity.Cart;
import com.example.pinkbullmakeup.Entity.Customer;
import com.example.pinkbullmakeup.Exceptions.ResourceNotFoundException;
import com.example.pinkbullmakeup.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findById(UUID customerId){
        return customerRepository.findById(customerId)
                .orElseThrow(()->new ResourceNotFoundException("Customer with id: " + customerId + " not found."));
    }

}

