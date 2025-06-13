package com.kritica.service;

import com.kritica.exception.APIException;
import com.kritica.model.Category;
import com.kritica.model.Products;
import com.kritica.payload.ProductDTO;
import com.kritica.payload.ProductResponse;
import com.kritica.repository.CategoryRepository;
import com.kritica.repository.ProductsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductsRepository productsRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;

    @Value("${image.upload.dir}")
    private String path;

    public ProductServiceImpl(CategoryRepository categoryRepository,
                              ProductsRepository productsRepository,
                              ModelMapper modelMapper,
                              FileService fileService) {
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
       //product size is 0 or not
        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByandOrder);
        Page<Products> products= productsRepository.findAll(pageDetails);
        if(products.isEmpty()){
            throw new APIException("No products found");
        }
        List<ProductDTO> productDtos =products.stream().map(product -> modelMapper.map(product,ProductDTO.class)).toList();
        return getProductResponse(products, productDtos);


    }

    @Override
    public ProductDTO addProductToCategory(Long categoryId, ProductDTO productDto) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new APIException("Category with id "+categoryId+" not found"));

        //chek if the product is present or not
        List<Products> products = category.getProductsCategoryList();
        for (Products ps: products){
            if(ps.getName().equalsIgnoreCase(productDto.getName())){
                throw new APIException("Product with name "+productDto.getName()+" already exists in category "+category.getName());
            }
        }

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

        //product size is 0 or not
        if(products.isEmpty()){
            throw new APIException("No products found");
        }

        List<ProductDTO> productDtos = products.stream().map(product->modelMapper.map(product,ProductDTO.class)).toList();

        return getProductResponse(products, productDtos);
    }

    @Override
    public ProductResponse searchProduct(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        //implement pagination and sorting
        Sort sortByOrder=sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByOrder);
        Page<Products> products = productsRepository.findByNameLikeIgnoreCase("%" + keyword + "%",pageDetails);
        //product size is 0 or not
        if(products.isEmpty()){
            throw new APIException("No products found");
        }
        if(products.isEmpty()){
            throw new APIException("No products found for keyword "+keyword);
        }
       List<ProductDTO> productDtos= products.stream().map(product ->modelMapper.map(product,ProductDTO.class)).collect(toList());
        return getProductResponse(products, productDtos);
    }

    private ProductResponse getProductResponse(Page<Products> products, List<ProductDTO> productDtos) {
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

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //1. Get Product from product id
        Products product= productsRepository.findById(productId).orElseThrow(()->new APIException("Product with id "+productId+" not found"));
        if(image!=null){
            //2. upload image to the server
            //3.Get the file name of updated image
            String fileName = fileService.uploadImage(path,image);
            //4. updating the new file name to the product
            product.setImageUrl(fileName);
            //5. save update product
            Products updatedProduct=productsRepository.save(product);
            //6. return DTO after mapping the product to DTO
            return modelMapper.map(updatedProduct,ProductDTO.class);
        }
        throw new APIException("Image cannot be empty");
    }
}
