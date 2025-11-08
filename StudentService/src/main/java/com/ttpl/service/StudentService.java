package com.ttpl.service;

import com.ttpl.common.ApiResponse;
import com.ttpl.dto.request.StudentRequestDto;
import com.ttpl.dto.response.StudentResponseDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {

    ResponseEntity<?> createOrUpdateStudent(@RequestBody StudentRequestDto collegeRequestDto);

    ResponseEntity<?> getStudentById(Long id);

    ResponseEntity<?> getAllStudents();

    ResponseEntity<ApiResponse<List<StudentResponseDto>>> getStudentsByCollegeCode(String code);

    ResponseEntity<?> deleteById(Long id);

    ResponseEntity<?> deleteByClgCode(String code);

}
