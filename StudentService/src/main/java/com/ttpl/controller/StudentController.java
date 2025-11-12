package com.ttpl.controller;

import com.ttpl.common.ApiResponse;
import com.ttpl.dto.request.StudentRequestDto;
import com.ttpl.dto.response.StudentResponseDto;
import com.ttpl.service.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@SecurityRequirement(name="keycloak")
@Slf4j
public class StudentController {

    private final StudentService collegeService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity createOrUpdateCollege(@RequestBody @Valid StudentRequestDto dto) {
        return collegeService.createOrUpdateStudent(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCollegeById(@PathVariable Long id) {
        return collegeService.getStudentById(id);
    }

    @GetMapping("/all")
    public ResponseEntity getAllColleges() {
        return collegeService.getAllStudents();
    }

    @GetMapping("/clgCode/{code}")
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> getStudentsByCollegeCode(@PathVariable String code) {
        return collegeService.getStudentsByCollegeCode(code);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteByClgId(@PathVariable Long id) {
        return collegeService.deleteById(id);
    }

    @DeleteMapping("/clgCode/{code}")
    public ResponseEntity deleteByClgCode(@PathVariable String code) {
        return collegeService.deleteByClgCode(code);
    }


}
