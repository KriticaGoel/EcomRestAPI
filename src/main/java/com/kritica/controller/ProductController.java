package com.kritica.controller;

import com.kritica.config.AppConstants;
import com.kritica.payload.ProductDTO;
import com.kritica.payload.ProductResponse;
import com.kritica.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
        @RequestMapping("/api")
public class ProductController {

    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //1. PUBLIC - Retrieve all products - pageNumber, page Size,Sort by, SortOrder
        //Return Product Response, Https Status 200
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name="sortBy", defaultValue=AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER,required = false) String sortOrder){
        return ResponseEntity.ok(productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder));
    }    //2. PUBLIC - Get Product by id - product id is a path variable
        //Return Product DTO, Https Status 200
    @GetMapping("public/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductId(id));
    }

    //3. PUBLIC - Get Product by Category id - category id is a path variable - pageNumber, page Size,Sort by, SortOrder
       //Return Product Response, Https Status 200
    @GetMapping("public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductByCategoryId(@PathVariable Long categoryId,
          @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
          @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
          @RequestParam(name="sortBy", defaultValue=AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
          @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER,required = false) String sortOrder){
        return ResponseEntity.ok(productService.getProductByCategoryId(categoryId,pageNumber,pageSize,sortBy,sortOrder));
    }

    //4. PUBLIC - Search product by Keyword - keyword is a path variable - pageNumber, page Size,Sort by, SortOrder is Request Param
        //Return Product Response, Https Status 302
    @GetMapping("public/products/search/{keyword}")
    public ResponseEntity<ProductResponse> searchProduct(@PathVariable String keyword,
                 @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                 @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                 @RequestParam(name="sortBy", defaultValue=AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
                 @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER,required = false) String sortOrder){
        return new ResponseEntity<>(productService.searchProduct(keyword,pageNumber,pageSize,sortBy,sortOrder), HttpStatus.FOUND);
    }

    //5. ADMIN - Add a new product to the category - Category id is a path variable - product is a JSON return is Product DTO, Https Status 201
    @PostMapping("/admin/product/{categoryId}")
    public ResponseEntity<ProductDTO> addProductToCategory(@PathVariable Long categoryId,@Valid @RequestBody ProductDTO product){
        return ResponseEntity.ok(productService.addProductToCategory(categoryId,product));
    }
    //6. ADMIN - delete product by product id - Product id is a path variable - HttpStatus 200
    @DeleteMapping("admin/product/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
    //7. ADMIN - update an existing product - Product id path variable - Product is a JSON
    // return is Product DTO , HttpStatus 200
    @PutMapping("admin/product/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,@RequestBody ProductDTO product){
        return ResponseEntity.ok(productService.updateProduct(id,product));
    }
    //8. ADMIN - Update the image of a product - Product id path variable - Multipart file(form data)- Return ProductDTO 200
    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updateProduct = productService.updateProductImage(productId, image);
        return ResponseEntity.ok(updateProduct);
    }
    //9. SELLER - Get Product by seller - seller id is a path variable - pageNumber, page Size,Sort by, SortOrder- NA- Product Response, Https Status 200

    //10. ADMIN-Retrieve Product count - return long , 200

}
