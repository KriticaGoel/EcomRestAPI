package com.kritica.controller;

import com.kritica.config.AppConstants;
import com.kritica.payload.CategoryDTO;
import com.kritica.payload.CategoryResponse;
import com.kritica.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    //Get all Category
    @GetMapping("/")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(required = false,defaultValue = AppConstants.PAGE_NUMBER, name="pageNumber") Integer pageNumber,
            @RequestParam(required = false,defaultValue = AppConstants.PAGE_SIZE,name="pageSize") Integer pageSize,
            @RequestParam(required=false,defaultValue = AppConstants.SORT_CATAGORIES_BY,name="sortBy") String sortBy,
            @RequestParam(required = false, defaultValue = AppConstants.SORT_ORDER,name = "sortOrder") String sortOrder) {
       CategoryResponse response= categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
       if(response.getCategories().isEmpty()){
           return ResponseEntity.noContent().build();
       }
        return ResponseEntity.ok(response);
    }

    //Create a new Category
    //the validation will be triggered when the DTO is received by the controller ( on controller parameter) `@Valid`
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createNewCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO response = categoryService.createNewCategory(categoryDTO);
        return ResponseEntity.ok(response);

    }

    //Update Existing Category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        CategoryDTO response = categoryService.updateCategory(id,categoryDTO);
        return ResponseEntity.ok(response);
    }

    //Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id){
        CategoryDTO response = categoryService.deleteCategory(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    //get Category by name
    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name){
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    //get Category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}
