package com.example.pinkbullmakeup.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class OrderItem {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID orderItemId;

    @ManyToOne
    @JsonBackReference
    private Order order;

    @ManyToOne
    private Product product;
   private String shade;
    private int quantity;
    private float priceAtPurchase;

    public OrderItem() {}

    public OrderItem(Product product, int quantity, float priceAtPurchase, String shade) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
        this.shade = shade;
    }

    public String getShade() {
        return shade;
    }

    public void setShade(String shade) {
        this.shade = shade;
    }

    public UUID getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(UUID orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(float priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
}
