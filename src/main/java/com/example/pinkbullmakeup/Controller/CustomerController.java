package com.example.pinkbullmakeup.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customer Controller", description = "Endpoints for customer operations")
public class CustomerController {

    @GetMapping("/profile")
    @Operation(summary = "Customer Profile", description = "Returns a welcome message for the logged-in customer")
    public String customerProfile() {
        return "Welcome, Customer!";
    }
}
