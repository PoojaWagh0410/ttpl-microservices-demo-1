package com.ttpl.entity;

import com.ttpl.dto.response.StudentResponseDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "college", uniqueConstraints = {
        @UniqueConstraint(columnNames = "clg_code")
})
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clgName;

    @Column(unique = true, nullable = false)
    private String clgCode;

    private String  clgAddress;

    private Long phone;

    @Transient
    private List<StudentResponseDto> students;
}