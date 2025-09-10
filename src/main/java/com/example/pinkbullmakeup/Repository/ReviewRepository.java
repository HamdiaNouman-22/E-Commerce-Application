package com.example.pinkbullmakeup.Repository;

import com.example.pinkbullmakeup.Entity.Product;
import com.example.pinkbullmakeup.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByProduct(Product product);
}
