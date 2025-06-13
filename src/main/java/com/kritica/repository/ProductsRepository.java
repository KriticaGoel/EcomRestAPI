package com.kritica.repository;

import com.kritica.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
    List<Products> getProductsByCategory_Id(Long categoryId);
    Page<Products> findByCategory_Id(Long categoryId, Pageable pageable);

    Products getProductsById(Long productId);

    Page<Products> findByNameLikeIgnoreCase(String keyword, Pageable pageable);
}
