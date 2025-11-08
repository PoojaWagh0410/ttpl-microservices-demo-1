package com.ttpl.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CollegeResponseDto {

    private String clgName;

    private String clgCode;

    private String  clgAddress;

    private Long phone;

    private List<StudentResponseDto> students;
}