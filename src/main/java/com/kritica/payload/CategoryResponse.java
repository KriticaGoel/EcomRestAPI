package com.kritica.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//this class is a response sent to client
// server get data from repository in form of entity
//and converts entity to DTO
//This DTO sends a response back to a client
//this helps to decouple model and presentation layers
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private List<CategoryDTO> categories;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
    private Boolean lastPage;
    private Boolean firstPage;
    }
