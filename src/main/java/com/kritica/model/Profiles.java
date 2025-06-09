package com.kritica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profiles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq_gen")
    @SequenceGenerator(name = "profile_seq_gen", sequenceName = "profile_seq", allocationSize = 1, initialValue = 1)
    private int id;

    @Column(name = "email_address", unique = true, nullable = false, length = 100)
    private String emailAddress;
    @Column(name = "first_name", unique = true, nullable = false, length = 20)
    private String firstName;
    @Column(name = "last_name", unique = true, nullable = false, length = 20)
    private String lastName;
    @Column(name = "contact_number", unique = true, nullable = false, length = 15)
    private String contactNumber;

    @JsonBackReference("user-profiles") // Wrong: Owning side should use @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "users_id")
    private Users user;

}
