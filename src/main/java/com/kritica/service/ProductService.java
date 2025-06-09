package com.kritica.service;

import com.kritica.payload.ProductResponse;

public interface ProductService {

    public ProductResponse addProductToCategory(Integer categoryId, ProductResponse productDto);

}
