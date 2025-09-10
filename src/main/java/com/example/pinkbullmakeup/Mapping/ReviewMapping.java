package com.example.pinkbullmakeup.Mapping;

import com.example.pinkbullmakeup.DTO.AddReview;
import com.example.pinkbullmakeup.Entity.Customer;
import com.example.pinkbullmakeup.Entity.Product;
import com.example.pinkbullmakeup.Entity.Review;
import com.example.pinkbullmakeup.Service.CustomerService;
import com.example.pinkbullmakeup.Service.ProductService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ReviewMapping {

    @Mapping(target = "customer", expression = "java(mapCustomer(addReview.getCustomerId(), customerService))")
    @Mapping(target = "product", expression = "java(mapProduct(addReview.getProductId(), productService))")
    @Mapping(target = "review", source = "reviewMessage")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "reviewLeftAt", expression = "java(java.time.LocalDateTime.now())")
    Review toReview(AddReview addReview, @Context CustomerService customerService, @Context ProductService productService);

    // Helper methods for ID -> entity
    default Customer mapCustomer(String customerId, @Context CustomerService customerService) {
        return customerService.findById(UUID.fromString(customerId));
    }

    default Product mapProduct(String productId, @Context ProductService productService) {
        return productService.findById(UUID.fromString(productId));
    }
}
