package com.ttpl.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequestDto {

    private Long id;

    @NotNull(message = "Student name cannot be null")
    private String name;

    @Email(message = "Invalid email format!")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^[0-9]{10}$", message = "Please enter a valid 10-digit phone number")
    private String phone;

    private String city;

    private String clgCode;
}
