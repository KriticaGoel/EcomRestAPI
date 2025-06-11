package com.kritica.service;

import com.kritica.exception.APIException;
import com.kritica.model.Category;
import com.kritica.model.Products;
import com.kritica.payload.ProductDTO;
import com.kritica.payload.ProductResponse;
import com.kritica.repository.CategoryRepository;
import com.kritica.repository.ProductsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Validated
public class ProductServiceImpl implements ProductService {
    private final ProductsRepository productsRepository;
    private final CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public ProductServiceImpl(CategoryRepository categoryRepository,
                              ProductsRepository productsRepository,
                              ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByandOrder);
        Page<Products> products= productsRepository.findAll(pageDetails);

        List<ProductDTO> productDTOS =products.stream().map(product -> modelMapper.map(product,ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductResponse(productDTOS);
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setFirstPage(products.isFirst());
        productResponse.setLastPage(products.isLast());
        return productResponse;

    }

    @Override
    public ProductDTO addProductToCategory(Long categoryId, ProductDTO productDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new APIException("Category with id "+categoryId+" not found"));
        Products product =modelMapper.map(productDto,Products.class);
        product.setCategory(category);
        Products savedProduct=productsRepository.save(product);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO getProductId(Long productId) {
       Products product= productsRepository.findById(productId).orElseThrow(()->new APIException("Product with id "+productId+" not found"));
        return modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public ProductResponse getProductByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
       //implement pagination and sorting
        Sort sortByOrder=sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByOrder);
       // List<Products> products=productsRepository.getProductsByCategory_Id(categoryId);
        Page<Products> products=productsRepository.findByCategory_Id(categoryId,pageDetails);
        List<ProductDTO> productDtos = products.stream().map(product->modelMapper.map(product,ProductDTO.class)).toList();

        ProductResponse response = new ProductResponse();
        response.setProductResponse(productDtos);
        response.setTotalElements(products.getTotalElements());
        response.setTotalPages(products.getTotalPages());
        response.setPageSize(products.getSize());
        response.setFirstPage(products.isFirst());
        response.setLastPage(products.isLast());
        response.setPageNumber(products.getNumber());

        return response;
    }

    @Override
    public ProductResponse searchProduct(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        //implement pagination and sorting
        Sort sortByOrder=sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByOrder);
        Page<Products> products = productsRepository.findByNameLikeIgnoreCase("%" + keyword + "%",pageDetails);
        if(products==null || products.isEmpty()){
            throw new APIException("No products found for keyword "+keyword);
        }
       List<ProductDTO> productDtos= products.stream().map(product ->modelMapper.map(product,ProductDTO.class)).collect(toList());
       ProductResponse response = new ProductResponse();
       response.setProductResponse(productDtos);
       response.setTotalElements(products.getTotalElements());
       response.setTotalPages(products.getTotalPages());
       response.setPageSize(products.getSize());
       response.setFirstPage(products.isFirst());
       response.setLastPage(products.isLast());
       response.setPageNumber(products.getNumber());
       return response;
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDto) {
        Products product= productsRepository.findById(id).orElseThrow(()->new APIException("Product with id "+id+" not found"));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setUpdate_by("API");
        productsRepository.save(product);

        return modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long id) {
        Products product= productsRepository.findById(id).orElseThrow(()->new APIException("Product with id "+id+" not found"));

        productsRepository.deleteById(id);

        return modelMapper.map(product,ProductDTO.class);
    }
}
