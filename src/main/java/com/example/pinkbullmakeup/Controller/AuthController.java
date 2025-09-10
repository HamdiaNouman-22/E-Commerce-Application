package com.example.pinkbullmakeup.Controller;

import com.example.pinkbullmakeup.DTO.LoginDTO;
import com.example.pinkbullmakeup.DTO.SignupDTO;
import com.example.pinkbullmakeup.Entity.Admin;
import com.example.pinkbullmakeup.Entity.Cart;
import com.example.pinkbullmakeup.Entity.Customer;
import com.example.pinkbullmakeup.Repository.AdminRepository;
import com.example.pinkbullmakeup.Repository.CartRepository;
import com.example.pinkbullmakeup.Repository.CustomerRepository;
import com.example.pinkbullmakeup.Security.JwtUtil;
import com.example.pinkbullmakeup.Service.SuperbaseService;
import com.example.pinkbullmakeup.Service.UserContextService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "APIs for Admin and Customer signup, login, and user info")
public class AuthController {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserContextService userContextService;

    @Autowired
    private SuperbaseService service;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup/admin")
    @Operation(summary = "Admin Signup", description = "Registers a new admin user")
    public ResponseEntity<?> signupAdmin(@Valid @RequestBody SignupDTO request) {
        if (adminRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Admin already exists");
        }

        Admin admin = new Admin();
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        adminRepo.save(admin);
        return ResponseEntity.ok("Admin registered successfully");
    }

    @PostMapping("/signup/customer")
    @Operation(summary = "Customer Signup", description = "Registers a new customer")
    public ResponseEntity<?> signupCustomer(@Valid @RequestBody SignupDTO request) {
        if (customerRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Customer already exists");
        }

        Customer customer = new Customer();
        customer.setCity(request.getCity());
        customer.setPostalCode(request.getPostalCode());
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setPhoneNumber(request.getPhone());
        customer.setAddress(request.getAddress());

        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cartRepository.save(cart));

        customerRepo.save(customer);
        return ResponseEntity.ok("Customer registered successfully");
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Logs in a user (admin or customer) and returns a JWT token")
    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
        // Check in admin table
        Optional<Admin> admin = adminRepo.findByEmail(request.getEmail());
        if (admin.isPresent() && passwordEncoder.matches(request.getPassword(), admin.get().getPassword())) {
            String token = jwtUtil.generateToken(admin.get().getAdminId().toString(), "ADMIN");
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }

        // Check in customer table
        Optional<Customer> customer = customerRepo.findByEmail(request.getEmail());
        if (customer.isPresent() && passwordEncoder.matches(request.getPassword(), customer.get().getPassword())) {
            String token = jwtUtil.generateToken(customer.get().getUserId().toString(), "CUSTOMER");
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/me")
    @Operation(summary = "Get Logged-in User Info", description = "Returns details of the currently logged-in user")
    public ResponseEntity<?> getLoggedInUserInfo() {
        UUID userId = userContextService.getCurrentUserId();
        return ResponseEntity.ok(service.getUserDetailsById(userId));
    }
}
