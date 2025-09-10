package com.example.pinkbullmakeup.Controller;

import com.example.pinkbullmakeup.Entity.CartItem;
import com.example.pinkbullmakeup.Entity.Order;
import com.example.pinkbullmakeup.Exceptions.ResourceNotFoundException;
import com.example.pinkbullmakeup.Security.JwtUtil;
import com.example.pinkbullmakeup.Service.CartService;
import com.example.pinkbullmakeup.Service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer/orders")
@Tag(name = "Order Controller", description = "APIs for managing customer orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final JwtUtil jwtUtil;

    public OrderController(OrderService orderService, CartService cartService, JwtUtil jwtUtil) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.jwtUtil = jwtUtil;
    }

    private UUID extractUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // Remove "Bearer "
        return UUID.fromString(jwtUtil.extractUserId(token));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/place")
    @Operation(summary = "Place Order", description = "Creates a new order from the customer's cart items")
    public ResponseEntity<Order> placeOrder(HttpServletRequest request) {
        UUID userId = extractUserId(request);
        List<CartItem> cartItems = cartService.getAllCartItems(userId);

        if (cartItems.isEmpty()){
            throw new ResourceNotFoundException("Cart empty.");
        }

        Order order = orderService.generateOrder(userId, cartItems);
        return ResponseEntity.ok(order);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/cancel/{orderId}")
    @Operation(summary = "Cancel Order", description = "Cancels an existing order by order ID")
    public ResponseEntity<String> cancelOrder(@PathVariable UUID orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order canceled successfully.");
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    @Operation(summary = "Get My Orders", description = "Fetches all orders placed by the logged-in customer")
    public ResponseEntity<List<Order>> getMyOrders(HttpServletRequest request) {
        UUID userId = extractUserId(request);
        List<Order> orders = orderService.getAllOrdersOfUser(userId);
        return ResponseEntity.ok(orders);
    }
}
