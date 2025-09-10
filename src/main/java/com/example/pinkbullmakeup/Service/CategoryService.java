package com.example.pinkbullmakeup.Service;

import com.example.pinkbullmakeup.Entity.Category;
import com.example.pinkbullmakeup.Exceptions.AlreadyExistsException;
import com.example.pinkbullmakeup.Exceptions.ResourceNotFoundException;
import com.example.pinkbullmakeup.Repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



    public Category addCategory(String categoryName) {
        String name = categoryName.toLowerCase().trim();

        if (categoryRepository.existsByCategoryName(name)) {
            throw new AlreadyExistsException(name);
        }

        Category category = new Category();
        category.setCategoryName(name);

        return categoryRepository.save(category);
    }

    public Category updateCategory(String newCategoryName, UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found."));


        category.setCategoryName(newCategoryName);

        return categoryRepository.save(category);
    }

    public void deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found."));

        categoryRepository.delete(category);
    }


    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findByName(String categoryName) {
        return categoryRepository.findCategoryByCategoryName(categoryName.toLowerCase().trim())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
    }
}
