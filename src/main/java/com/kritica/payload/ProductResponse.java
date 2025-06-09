package com.kritica.payload;

import java.util.List;

public class ProductResponse {
    private List<ProductDTO> productResponse;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
    private Boolean lastPage;
    private Boolean firstPage;

}
