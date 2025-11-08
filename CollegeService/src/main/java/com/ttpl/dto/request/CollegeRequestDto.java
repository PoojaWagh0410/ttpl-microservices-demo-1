package com.ttpl.dto.request;

import jakarta.validation.constraints.NotNull;
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

    private String  clgAddress;

    private Long phone;
}