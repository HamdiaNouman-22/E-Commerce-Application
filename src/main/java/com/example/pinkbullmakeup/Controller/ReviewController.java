package com.example.pinkbullmakeup.Controller;

import com.example.pinkbullmakeup.DTO.AddReview;
import com.example.pinkbullmakeup.Entity.Review;
import com.example.pinkbullmakeup.Service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review Controller", description = "APIs for managing product reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    @Operation(summary = "Create a review", description = "Allows a customer to create a review for a product")
    public ResponseEntity<Review> createReview(@Valid @RequestBody AddReview addReview) {
        Review review = reviewService.createReview(addReview);
        return ResponseEntity.created(URI.create("/api/reviews/" + review.getReviewId())).body(review);
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get reviews by product", description = "Retrieves all reviews for a specific product by product ID")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable UUID productId) {
        List<Review> reviews = reviewService.getAllReviewsOfProduct(productId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review", description = "Deletes a review by its ID")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
