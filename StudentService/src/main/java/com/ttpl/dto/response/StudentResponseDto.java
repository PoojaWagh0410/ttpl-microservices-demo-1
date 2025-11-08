package com.ttpl.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {

    private String name;

    private String email;

    private String phone;

    private String city;

    private String clgCode;

}
