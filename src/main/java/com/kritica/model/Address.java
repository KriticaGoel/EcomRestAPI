package com.kritica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "address_seq_gen")
    @SequenceGenerator(name = "address_seq_gen", sequenceName = "address_seq", allocationSize = 1)
    private int id;

    @Column(name = "address_line_1",nullable = false,length = 50)
    private String addressLine1;
    @Column(name = "address_line_2",length = 50)
    private String addressLine2;
    @Column(name = "city",length = 50)
    private String city;
    @Column(name = "state",length = 50)
    private String state;
    @Column(name = "country",length = 50)
    private String country;
    @Column(name = "zip_code",length = 10)
    private String zipCode;
    @Column(name = "create_at")
    private String create_by;
    @Column(name = "update_at")
    private String update_by;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<Users> userAddressList = new ArrayList<>();

}
