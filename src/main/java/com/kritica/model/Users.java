package com.kritica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Column(name = "password", unique = false, nullable = false, length = 100)
    private String password;

    @Column(name = "email_address", unique = true, nullable = false, length = 100)
    private String emailAddress;
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},
    fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    //to make bidirection -> we want to access profile class data usimg user class
    //and we dont want any foreign key of profile in user
    //use - Mapped by and use variable name of profile
    @JsonManagedReference("user-profiles")
    @OneToOne(mappedBy = "user")
    //  @JsonIgnore
    private Profiles profile;

      @JsonIgnore
    @JsonManagedReference("user-orders") // Wrong: Non-owning side should use @JsonBackReference
    @OneToMany(mappedBy = "users")
    private List<Orders> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Products> productsSet = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinTable(name="user_address",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="address_id"))
    private List<Address> addresses;


    public Users(@NotNull(message = "username cannot be null") @Size(min=3, max=20,message="username must contain at least 3 character") String username, String encode, @Email(message = "Email is not valid") @NotNull(message = "Email cannot be null") @Size(min=3, max=50, message="Email must contain at least 3 character") String email) {
    }
}
