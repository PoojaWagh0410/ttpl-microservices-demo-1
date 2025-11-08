package com.ttpl.client;

import com.ttpl.common.ApiResponse;
import com.ttpl.dto.response.StudentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value="studentservice", url="http://student-service:8081")
public interface StudentServiceClient {

    @GetMapping("/student/clg-code/{clgCode}")
    ResponseEntity<ApiResponse<List<StudentResponseDto>>> getStudentsByClgCode(@PathVariable String clgCode) ;

    @DeleteMapping("/student/clg-code/{code}")
    ResponseEntity<?> deleteByClgCode(@PathVariable String code);
}
