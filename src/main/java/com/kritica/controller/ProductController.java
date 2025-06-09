package com.kritica.controller;

import com.kritica.payload.ProductDTO;
import com.kritica.payload.ProductResponse;
import com.kritica.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private ProductService productService;
    public ProductController(ProductService productService) {
        productService = productService;
    }

    //1. PUBLIC -Retrieve all products - pageNumber, page Size,Sort by, SortOrder- NA- Product Response, Https Status 200

    //2. PUBLIC - Get Product by Id - product id is a path variable - Product DTO, Https Status 200

    //3. PUBLIC -Get Product by Category id - category id is a path variable - pageNumber, page Size,Sort by, SortOrder- NA- Product Response, Https Status 200

    //4. PUBLIC - Search product by Keyword - keyword is a path variable - pageNumber, page Size,Sort by, SortOrder is Request Param- NA- Product Response, Https Status 302

    //5. ADMIN - Add a new product to category - Category id is a path variable - product is a JSON return is Product DTO, Https Status 201

    @PostMapping("/admin/product/{categoryId}")
    public ResponseEntity<ProductDTO> addProductToCategory(@PathVariable Long categoryId, @RequestBody ProductDTO product){

        return null;
    }
    //6. ADMIN - delete product by product id - Product id is a path variable - HttpStatus 200

    //7. ADMIN - update an existing product- Product id path variable - Product is a JSON return is Product DTO , HttpStatus 200

    //8. ADMIN - Update the image of a product- Product id path variable - Multipart file(form data)- Return ProductDTO 200

    //9. SELLER - Get Product by seller - seller id is a path variable - pageNumber, page Size,Sort by, SortOrder- NA- Product Response, Https Status 200

    //10. ADMIN-Retrieve Product count- return long , 200

}
