package com.ttpl.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequestDto {

    private Long id;

    private String name;

    private String email;

    private String  phone;

    private String city;

    private String clgCode;
}
