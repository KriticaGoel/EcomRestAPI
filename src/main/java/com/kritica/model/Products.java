package com.kritica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    @SequenceGenerator(name = "product_seq_gen", sequenceName = "product_seq", allocationSize = 1, initialValue = 1)
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String imageAlt;
    private String create_by;
    private String update_by;
    private Double price;
    private Boolean sellable;
    private Boolean returnable;
    private Integer minThreshold;
    @CreationTimestamp
    //  @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

//    @ManyToMany
//    @JoinTable(name = "product_users",
//            joinColumns = @JoinColumn(name = "products_id"),
//            inverseJoinColumns = @JoinColumn(name = "users_id"))
//    private Set<Users> usersSet = new HashSet<>();
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
}
