package com.kritica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "role")
@Entity
public class Role {
    @Id
    @GeneratedValue(generator = "role_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "role_seq_gen", sequenceName = "role_seq", allocationSize = 1)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Length(min = 3, max = 20)
    private AppRole roleName;
}
