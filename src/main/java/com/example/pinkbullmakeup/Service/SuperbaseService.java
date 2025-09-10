package com.example.pinkbullmakeup.Service;

import com.example.pinkbullmakeup.DTO.UserDetailsDTO;
import com.example.pinkbullmakeup.Entity.Admin;
import com.example.pinkbullmakeup.Entity.Customer;
import com.example.pinkbullmakeup.Repository.AdminRepository;
import com.example.pinkbullmakeup.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SuperbaseService {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private CustomerRepository customerRepo;

    public String getRoleByEmail(String email) {
        if (adminRepo.findByEmail(email).isPresent()) {
            return "ADMIN";
        } else if (customerRepo.findByEmail(email).isPresent()) {
            return "CUSTOMER";
        }
        return null;
    }

    public UserDetailsDTO getUserDetailsById(UUID userId) {
        Optional<Admin> admin = adminRepo.findByadminId(userId);
        if (admin.isPresent()) {
            Admin a = admin.get();
            return new UserDetailsDTO(a.getAdminId(), a.getName(), a.getEmail(), "ADMIN");
        }

        // Check Customer
        Optional<Customer> customer = customerRepo.findById(userId);
        if (customer.isPresent()) {
            Customer c = customer.get();
            return new UserDetailsDTO(
                    c.getUserId(),
                    c.getName(),
                    c.getEmail(),
                    "CUSTOMER",
                    c.getPhoneNumber(),     // assuming getter exists
                    c.getAddress()    // assuming getter exists
            );
        }

        return null;
    }


}
