package com.example.pinkbullmakeup.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class CartItem {
    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID cartItemId;
    @ManyToOne
    private Product productInCart;
    private int productQuantity;
    private float priceAccordingToQuantity;
    private String shade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Cart cart;
    
    public CartItem() {}

    public CartItem(Product productInCart, int productQuantity, String shade) {
        this.productInCart = productInCart;
        this.productQuantity = productQuantity;
        this.shade = shade;
        calculateAllItemsPrice();
    }

    public UUID getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(UUID cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Product getProductInCart() {
        return productInCart;
    }

    public void setProductInCart(Product productInCart) {
        this.productInCart = productInCart;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
        calculateAllItemsPrice();
    }

    public float getPriceAccordingToQuantity() {
        return priceAccordingToQuantity;
    }

    public void setPriceAccordingToQuantity(float priceAccordingToQuantity) {
        this.priceAccordingToQuantity = priceAccordingToQuantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getShade() {
        return shade;
    }

    public void setShade(String shade) {
        this.shade = shade;
    }

    public void calculateAllItemsPrice() {
        this.priceAccordingToQuantity = priceAccordingToQuantity * productQuantity;
    }

}
