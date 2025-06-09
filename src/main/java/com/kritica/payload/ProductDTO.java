package com.kritica.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotNull(message = "Category name cannot be null")
    @Size(message = "Category must contain at least 3 character", min = 3, max = 20)
    String name;
    @Size(message = "Description must contain at most 150 character", max = 150)
    String description;
    @Size(message = "Parent category must contain at most 150 character", max = 150)
    String parentCategory;
    String imageUrl;
    String imageAlt;
    Double price;
    Boolean sellable;
    Boolean returnable;
    Integer minThreshold;
    @Size(message = "Create by must contain at most 20 character", max = 20)
    String create_by;
    @Size(message = "Update by must contain at most 20 character", max = 20)
    String update_by;
    Date createdAt;
    Date updatedAt;
}
