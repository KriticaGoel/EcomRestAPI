package com.kritica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name"})},
        indexes ={
            @Index(name = "idx_category_name", columnList = "name"),
                @Index(name = "idx_category_created_at", columnList = "created_at")}
        )

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_seq_gen")
    @SequenceGenerator(name = "cat_seq_gen", sequenceName = "CAT_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull(message = "Category name cannot be null")
    @Size(min=3, max=20, message="Category must contain at least 3 character")
    @Column(name = "name")
    private String name;
    @Size(max=150, message="Description must contain at most 150 character")
    @Column(name = "description")
    private String description;
    @Column(name="parent_category")
    @Size(max=150 , message = "Parent category must contain at most 150 character")
    private String parentCategory;

    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "image_alt")
    private String imageAlt;
    @Column(name = "create_by")
    @Size(max=20, message="Create by must contain at most 20 character")
    private String create_by;
    @Column(name = "update_by")
    @Size(max=20, message="Update by must contain at most 20 character")
    private String update_by;

    @CreationTimestamp
  //  @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Products> productsCategoryList;

}
