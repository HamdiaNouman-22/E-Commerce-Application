package com.example.pinkbullmakeup.Entity;

import com.example.pinkbullmakeup.ENUMS.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Delivery {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID deliveryId;

    @NotBlank
    private String deliveryAddress;

    private LocalDate estimatedDeliveryDate;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    @JsonBackReference
    private Order order;

    public Delivery() {}

    public Delivery(String deliveryAddress, LocalDate estimatedDeliveryDate, DeliveryStatus status, Order order) {
        this.deliveryAddress = deliveryAddress;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.status = status;
        this.setOrder(order); // Sets both sides of the relationship
    }

    // Getters and setters

    public UUID getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDate getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        if (order != null && order.getDeliveryDetails() != this) {
            order.setDeliveryDetails(this);
        }
    }
}
