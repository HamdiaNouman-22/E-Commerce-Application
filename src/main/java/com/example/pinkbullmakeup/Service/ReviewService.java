package com.example.pinkbullmakeup.Service;

import com.example.pinkbullmakeup.DTO.AddReview;
import com.example.pinkbullmakeup.Entity.Product;
import com.example.pinkbullmakeup.Entity.Review;
import com.example.pinkbullmakeup.Exceptions.ResourceNotFoundException;
import com.example.pinkbullmakeup.Mapping.ReviewMapping;
import com.example.pinkbullmakeup.Repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final ReviewMapping reviewMapping;

    public ReviewService(ReviewRepository reviewRepository, ProductService productService, CustomerService customerService, ReviewMapping reviewMapping) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.customerService = customerService;
        this.reviewMapping = reviewMapping;
    }

    public Review createReview(AddReview addReview){
        Review review = reviewMapping.toReview(addReview,customerService,productService);

        reviewRepository.save(review);

        return review;
    }

    public List<Review> getAllReviewsOfProduct(UUID productId){
        Product product = productService.findById(productId);

        return product.getReviews();
    }

    public void deleteReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review with id:" + reviewId + " not found."));

        reviewRepository.delete(review);
    }

}
