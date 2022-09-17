package com.example.wizerassessment.services;

import com.example.wizerassessment.mapper.DTOS.CategoryDTO;
import com.example.wizerassessment.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDTO createCategory(Category category) throws Exception;

    Optional<CategoryDTO> editCategory(Category category, Long id);

    List<CategoryDTO> getCategories();

    void deleteCategory(Long id);
    CategoryDTO saveAndReturnDTO(Category category);

    Category getCategoryByName(String name);
}
