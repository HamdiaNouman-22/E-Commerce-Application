package com.example.pinkbullmakeup.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Controller", description = "Endpoints for Admin operations")
public class AdminController {

    @GetMapping("/dashboard")
    @Operation(summary = "Admin Dashboard", description = "Returns a welcome message for the admin")
    public String adminDashboard() {
        return "Welcome, Admin!";
    }
}
