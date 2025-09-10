package com.example.pinkbullmakeup.Repository;

import com.example.pinkbullmakeup.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    public boolean existsByCategoryName(String categoryName);

    public Optional<Category> findCategoryByCategoryName(String categoryName);
}
