package com.example.pinkbullmakeup.Entity;

import com.example.pinkbullmakeup.ENUMS.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Payment {
    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String stripePaymentIntentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @NotNull
    private double amount;

    @PastOrPresent
    private LocalDateTime createdAt;

    @PastOrPresent
    private LocalDateTime confirmedAt;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Order order;

    public Payment() {}

    public Payment(String stripePaymentIntentId, PaymentStatus paymentStatus, double amount, LocalDateTime createdAt, LocalDateTime confirmedAt, Customer customer, Order order) {
        this.stripePaymentIntentId = stripePaymentIntentId;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.createdAt = createdAt;
        this.confirmedAt = confirmedAt;
        this.customer = customer;
        this.order = order;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStripePaymentIntentId() {
        return stripePaymentIntentId;
    }

    public void setStripePaymentIntentId(String stripePaymentIntentId) {
        this.stripePaymentIntentId = stripePaymentIntentId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}


