package com.example.pinkbullmakeup.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Cart {
    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> itemsInCart = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Customer customer;

    public Cart() {}

    public Cart(List<CartItem> itemsInCart) {
        this.itemsInCart = itemsInCart;
    }

    public float getCartTotalPrice(){
        float price = 0;
        for (CartItem item : itemsInCart ){
            price += item.getPriceAccordingToQuantity();
        }
        return price;
    }

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public List<CartItem> getItemsInCart() {
        return itemsInCart;
    }

    public void setItemsInCart(List<CartItem> itemsInCart) {
        this.itemsInCart = itemsInCart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

