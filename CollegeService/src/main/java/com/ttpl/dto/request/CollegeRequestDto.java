package com.ttpl.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollegeRequestDto {

    private Long id;

    @NotNull(message = "College name cannot be null")
    private String clgName;

    @NotNull(message = "College code cannot be null")
    private String clgCode;

    private String clgAddress;

    @NotNull(message = "Phone number cannot be null")
    @Min(value = 1000000000L, message = "Please enter a valid 10-digit phone number")
    @Max(value = 9999999999L, message = "Please enter a valid 10-digit phone number")
    private Long phone;
}