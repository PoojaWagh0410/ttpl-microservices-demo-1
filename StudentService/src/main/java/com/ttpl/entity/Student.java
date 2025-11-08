package com.ttpl.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
@Table(name = "student", uniqueConstraints = {
@UniqueConstraint(columnNames = "email, phone")
})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email(message = "Invalid email format!")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true,nullable = false)
    private String  phone;

    private String city;

    private String clgCode;

}
