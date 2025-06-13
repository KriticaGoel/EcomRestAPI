package com.kritica.service;

import com.kritica.payload.ProductDTO;
import com.kritica.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
    public ProductDTO addProductToCategory(Long categoryId,  ProductDTO productDto);
    public ProductDTO getProductId(Long id);
    public ProductResponse getProductByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
    public ProductResponse searchProduct(String searchTerm, Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
    public ProductDTO updateProduct(Long id, ProductDTO productDto);
    public ProductDTO deleteProduct(Long id);

    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
