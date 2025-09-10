package com.example.pinkbullmakeup.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Review {
    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID reviewId;

    @ManyToOne
    @NotNull
    @JsonBackReference
    private Product product;

    @ManyToOne
    @NotNull
    private Customer customer;

    @Size(max = 50)
    private String review;

    @Max(5)
    @Min(1)
    @NotNull
    private int rating;

    @PastOrPresent
    @NotNull
    private LocalDateTime reviewLeftAt;

    public Review() {}

    public Review(Product product, Customer customer, String review, int rating, LocalDateTime reviewLeftAt) {
        this.product = product;
        this.customer = customer;
        this.review = review;
        this.rating = rating;
        this.reviewLeftAt = reviewLeftAt;
    }

    @PreRemove
    public void removeFromProduct() {
        if (product != null) {
            product.getReviews().remove(this);
        }
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public void setReviewId(UUID reviewId) {
        this.reviewId = reviewId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getReviewLeftAt() {
        return reviewLeftAt;
    }

    public void setReviewLeftAt(LocalDateTime reviewLeftAt) {
        this.reviewLeftAt = reviewLeftAt;
    }
}
