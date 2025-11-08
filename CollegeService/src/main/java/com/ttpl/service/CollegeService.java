package com.ttpl.service;

import com.ttpl.common.ApiResponse;
import com.ttpl.dto.request.CollegeRequestDto;
import com.ttpl.dto.response.CollegeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface CollegeService {

    ResponseEntity<?> createOrUpdateCollege(@RequestBody CollegeRequestDto collegeRequestDto);

    ResponseEntity<?> getCollegeById(Long id);

    ResponseEntity<?> getAllCollege();

    ResponseEntity<ApiResponse<CollegeResponseDto>> getByCollegeCode(String code);

    ResponseEntity<?> deleteById(Long id);

    ResponseEntity<?> deleteByClgCode(String code);

}
