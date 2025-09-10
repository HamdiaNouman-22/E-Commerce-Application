package com.example.pinkbullmakeup.Controller;

import com.example.pinkbullmakeup.Entity.Category;
import com.example.pinkbullmakeup.Service.CategoryService;
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
@RequestMapping("/api/categories")
@Tag(name = "Category Controller", description = "APIs for managing product categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create Category", description = "Adds a new category. Admin access only.")
    public ResponseEntity<Category> createCategory(@Valid @RequestParam String categoryName) {
        Category category = categoryService.addCategory(categoryName);
        return ResponseEntity.created(URI.create("/api/categories/" + category.getCategoryId())).body(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update Category", description = "Updates an existing category by ID. Admin access only.")
    public ResponseEntity<Category> updateCategory(@Valid @RequestParam String newCategoryName, @PathVariable UUID id) {
        Category updatedCategory = categoryService.updateCategory(newCategoryName, id);
        return ResponseEntity.ok(updatedCategory);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Category", description = "Deletes a category by ID. Admin access only.")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get All Categories", description = "Returns a list of all categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search")
    @Operation(summary = "Get Category by Name", description = "Finds a category by its name")
    public ResponseEntity<Category> getCategoryByName(@RequestParam String categoryName) {
        Category category = categoryService.findByName(categoryName);
        return ResponseEntity.ok(category);
    }
}
