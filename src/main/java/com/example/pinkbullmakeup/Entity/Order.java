package com.example.pinkbullmakeup.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID orderId;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Customer user;

    @NotNull
    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @NotNull
    @Min(0)
    private float totalPrice;

    @PastOrPresent
    private LocalDateTime timeOrderPlaced;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Delivery deliveryDetails;

    public Order() {}

    public Order(Customer user, List<OrderItem> orderItems, float totalPrice, LocalDateTime timeOrderPlaced, Delivery deliveryDetails) {
        this.user = user;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.timeOrderPlaced = timeOrderPlaced;
        this.setDeliveryDetails(deliveryDetails); // Sets both sides of the relationship
    }

    // Getters and setters

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        // Establish bidirectional relationships
        for (OrderItem item : orderItems) {
            item.setOrder(this);
        }
    }


    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getTimeOrderPlaced() {
        return timeOrderPlaced;
    }

    public void setTimeOrderPlaced(LocalDateTime timeOrderPlaced) {
        this.timeOrderPlaced = timeOrderPlaced;
    }

    public Delivery getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(Delivery deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
        if (deliveryDetails != null) {
            deliveryDetails.setOrder(this);
        }
    }
}
