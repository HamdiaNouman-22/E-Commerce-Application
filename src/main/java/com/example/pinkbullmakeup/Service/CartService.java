package com.example.pinkbullmakeup.Service;

import com.example.pinkbullmakeup.DTO.ItemToAddInCart;
import com.example.pinkbullmakeup.Entity.Cart;
import com.example.pinkbullmakeup.Entity.CartItem;
import com.example.pinkbullmakeup.Entity.Customer;
import com.example.pinkbullmakeup.Exceptions.ResourceNotFoundException;
import com.example.pinkbullmakeup.Mapping.ItemMapping;
import com.example.pinkbullmakeup.Repository.CartItemRepository;
import com.example.pinkbullmakeup.Repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ItemMapping itemMapping;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;
    private final CustomerService customerService;

    public CartService(CartRepository cartRepository, ItemMapping itemMapping, ProductService productService, CartItemRepository cartItemRepository, CustomerService customerService) {
        this.cartRepository = cartRepository;
        this.itemMapping = itemMapping;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
        this.customerService = customerService;
    }

    public CartItem decreaseCartInItemQuantity(UUID cartItemId, int quantityToDecrease) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Item not found in cart."));

        if (quantityToDecrease >= cartItem.getProductQuantity()) {
            throw new IllegalArgumentException("Quantity to decrease is greater than or equal to current quantity.");
        }

        cartItem.setProductQuantity(cartItem.getProductQuantity() - quantityToDecrease);
        cartItemRepository.save(cartItem);

        return cartItem;
    }

    public CartItem increaseCartItemQuantity(UUID cartItemId, int quantityToIncrease) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Item not found in cart."));

        int newQuantity = cartItem.getProductQuantity() + quantityToIncrease;

        if (newQuantity > 100) {
            throw new IllegalArgumentException("Quantity cannot exceed the maximum allowed limit of 100.");
        }

        cartItem.setProductQuantity(newQuantity);
        cartItemRepository.save(cartItem);

        return cartItem;
    }

//    public CartItem addItemInCart(ItemToAddInCart itemToAddInCart, UUID userId) {
//        CartItem newItem = itemMapping.toCartItem(itemToAddInCart, productService);
//
//        if (!(newItem.getProductInCart().searchShadeInProductList(itemToAddInCart.getShadeName().toLowerCase().trim()))){
//            throw new ResourceNotFoundException("Shade:" + itemToAddInCart.getShadeName() + " not found.");
//        }
//
//        newItem.setShade(itemToAddInCart.getShadeName());
//
//        Customer customer = customerService.findById(userId);
//
//        Cart cart = customer.getCart();
//
//        if (cart == null) {
//            // Initialize the cart if null (if logic allows)
//            cart = new Cart();
//            cart.setCustomer(customer);
//            customer.setCart(cart); // Set the cart to the customer
//            cartRepository.save(cart); // Persist the new cart
//        }
//
//        UUID cartId = cart.getCartId();
//        UUID productId = newItem.getProductInCart().getProductId();
//
//        Optional<CartItem> existingItemOpt = cartItemRepository.findByCart_CartIdAndProductInCart_ProductId(cartId, productId);
//
//        if (existingItemOpt.isPresent()) {
//            CartItem existingItem = existingItemOpt.get();
//            int updatedQuantity = existingItem.getProductQuantity() + newItem.getProductQuantity();
//
//            if (updatedQuantity > 100) {
//                throw new IllegalArgumentException("Quantity cannot exceed the maximum allowed limit of 100.");
//            }
//
//            existingItem.setProductQuantity(updatedQuantity);
//
//            cartItemRepository.save(existingItem);
//        } else {
//            cartItemRepository.save(newItem);
//        }
//
//        return existingItemOpt.orElse(newItem);
//
//    }

    public CartItem addItemInCart(ItemToAddInCart itemToAddInCart, UUID userId) {
        CartItem newItem = itemMapping.toCartItem(itemToAddInCart, productService);

        if (!newItem.getProductInCart().searchShadeInProductList(itemToAddInCart.getShadeName().toLowerCase().trim())) {
            throw new ResourceNotFoundException("Shade: " + itemToAddInCart.getShadeName() + " not found.");
        }

        newItem.setShade(itemToAddInCart.getShadeName());

        Customer customer = customerService.findById(userId);

        Cart cart = customer.getCart();

        if (cart == null) {
            throw new IllegalArgumentException("No null values allowed.");
        }

        UUID cartId = cart.getCartId();
        UUID productId = newItem.getProductInCart().getProductId();

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCart_CartIdAndProductInCart_ProductId(cartId, productId);

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            int updatedQuantity = existingItem.getProductQuantity() + newItem.getProductQuantity();

            if (updatedQuantity > 100) {
                throw new IllegalArgumentException("Quantity cannot exceed the maximum allowed limit of 100.");
            }

            existingItem.setProductQuantity(updatedQuantity);
            existingItem.setPriceAccordingToQuantity(updatedQuantity * existingItem.getProductInCart().getProductPrice());
            cartItemRepository.save(existingItem);

            return existingItem;
        } else {
            newItem.setCart(cart);
            newItem.setProductQuantity(itemToAddInCart.getProductQuantity());
            newItem.setPriceAccordingToQuantity(newItem.getProductInCart().getProductPrice() * itemToAddInCart.getProductQuantity());
            cartItemRepository.save(newItem);
            return newItem;
        }
    }


    public void deleteItemFromCart(UUID userId, UUID cartItemId) {
        Customer customer = customerService.findById(userId);

        UUID cartId = customer.getCart().getCartId();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found."));

        if (!cartItem.getCart().getCartId().equals(cartId)) {
            throw new IllegalArgumentException("CartItem does not belong to the specified cart.");
        }

        cartItemRepository.delete(cartItem);
    }

    @Transactional(readOnly = true)
    public List<CartItem> getAllCartItems(UUID userId) {
        Customer customer = customerService.findById(userId);

        Cart cart = customer.getCart();

        return cart.getItemsInCart();
    }

    public void clearCart(UUID userId) {
        Cart cart = cartRepository.findByCustomer_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for the user."));

        if (cart.getItemsInCart().isEmpty()) {
            return;
        }

        try {
            cart.getItemsInCart().clear();
            cartRepository.save(cart);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to clear the cart. Please try again.");
        }
    }

    @Transactional(readOnly = true)
    public float getCartTotalPrice(UUID userId) {
        Customer customer = customerService.findById(userId);

        Cart cart = customer.getCart();

        return cart.getCartTotalPrice();
    }


    @Transactional(readOnly = true)
    public Cart findById(UUID cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart with id:" + cartId + " not found."));
    }

    @Transactional(readOnly = true)
    public Cart findByUserId(UUID userId) {
        return cartRepository.findByCustomer_UserId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found."));
    }
}
