package com.example.pinkbullmakeup.Repository;

import com.example.pinkbullmakeup.Entity.Brand;
import com.example.pinkbullmakeup.Entity.Category;
import com.example.pinkbullmakeup.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByProductNameAndProductBrandAndProductCategory(
            String productName,
            Brand productBrand,
            Category productCategory
    );

    List<Product> findAllByProductBrand(Brand brand);

    List<Product> findAllByProductCategory(Category category);

    List<Product> findAllByOrderByProductPriceAsc();

    List<Product> findAllByOrderByProductPriceDesc();
}
