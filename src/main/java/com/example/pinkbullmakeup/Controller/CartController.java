package com.example.pinkbullmakeup.Controller;

import com.example.pinkbullmakeup.DTO.ItemToAddInCart;
import com.example.pinkbullmakeup.Entity.Cart;
import com.example.pinkbullmakeup.Entity.CartItem;
import com.example.pinkbullmakeup.Security.JwtUtil;
import com.example.pinkbullmakeup.Service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Controller", description = "APIs for managing customer shopping carts")
public class CartController {

    private final CartService cartService;
    private final JwtUtil jwtUtil;

    public CartController(CartService cartService, JwtUtil jwtUtil) {
        this.cartService = cartService;
        this.jwtUtil = jwtUtil;
    }

    private UUID extractUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // remove "Bearer "
        return UUID.fromString(jwtUtil.extractUserId(token));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/items")
    @Operation(summary = "Get Cart Items", description = "Fetch all items in the logged-in customer's cart")
    public ResponseEntity<List<CartItem>> getCartItems(HttpServletRequest request) {
        UUID userId = extractUserId(request);
        return ResponseEntity.ok(cartService.getAllCartItems(userId));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add")
    @Operation(summary = "Add Item to Cart", description = "Adds a product to the logged-in customer's cart")
    public ResponseEntity<CartItem> addItemToCart(@Valid @RequestBody ItemToAddInCart item, HttpServletRequest request) {
        UUID userId = extractUserId(request);
        return ResponseEntity.ok(cartService.addItemInCart(item, userId));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/increase/{cartItemId}")
    @Operation(summary = "Increase Cart Item Quantity", description = "Increases the quantity of a cart item")
    public ResponseEntity<CartItem> increaseQuantity(
            @PathVariable UUID cartItemId,
            @RequestParam int quantity,
            HttpServletRequest request) {
        return ResponseEntity.ok(cartService.increaseCartItemQuantity(cartItemId, quantity));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/decrease/{cartItemId}")
    @Operation(summary = "Decrease Cart Item Quantity", description = "Decreases the quantity of a cart item")
    public ResponseEntity<CartItem> decreaseQuantity(
            @PathVariable UUID cartItemId,
            @RequestParam int quantity,
            HttpServletRequest request) {
        return ResponseEntity.ok(cartService.decreaseCartInItemQuantity(cartItemId, quantity));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/remove/{cartItemId}")
    @Operation(summary = "Remove Item from Cart", description = "Removes a specific item from the logged-in customer's cart")
    public ResponseEntity<String> deleteItem(@PathVariable UUID cartItemId, HttpServletRequest request) {
        UUID userId = extractUserId(request);
        cartService.deleteItemFromCart(userId, cartItemId);
        return ResponseEntity.ok("Item removed from cart.");
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/total")
    @Operation(summary = "Get Cart Total", description = "Calculates the total price of all items in the logged-in customer's cart")
    public ResponseEntity<Float> getCartTotal(HttpServletRequest request) {
        UUID userId = extractUserId(request);
        return ResponseEntity.ok(cartService.getCartTotalPrice(userId));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    @Operation(summary = "Get Cart", description = "Fetches the full cart details of the logged-in customer")
    public ResponseEntity<Cart> getCart(HttpServletRequest request) {
        UUID userId = extractUserId(request);
        return ResponseEntity.ok(cartService.findByUserId(userId));
    }
}
