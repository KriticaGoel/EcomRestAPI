package com.kritica.service;

import com.kritica.payload.CategoryDTO;
import com.kritica.payload.CategoryResponse;
import jakarta.validation.Valid;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
    CategoryDTO createNewCategory(@Valid CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long id);
    CategoryDTO getCategoryByName(String name);
    CategoryDTO getCategoryById(Long id);

}
