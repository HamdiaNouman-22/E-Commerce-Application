package com.example.pinkbullmakeup.Repository;

import com.example.pinkbullmakeup.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    public boolean existsByBrandName(String brandName);

    public Optional<Brand> findBrandByBrandName(String brandName);

}
