package com.kritica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", allocationSize = 1, initialValue = 1)
    private int id;
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;
    @Column(name = "password", unique = true, nullable = false, length = 100)
    private String password;


    //to make bidirection -> we want to access profile class data usimg user class
    //and we dont want any foreign key of profile in user
    //use - Mapped by and use variable name of profile
    @JsonManagedReference("user-profiles")
    @OneToOne(mappedBy = "user")
    //  @JsonIgnore
    private Profiles profile;

    //  @JsonIgnore
    @JsonManagedReference("user-orders") // Wrong: Non-owning side should use @JsonBackReference
    @OneToMany(mappedBy = "users")
    private List<Orders> orders;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "usersSet")
//    private Set<Products> productsSet = new HashSet<>();

}
