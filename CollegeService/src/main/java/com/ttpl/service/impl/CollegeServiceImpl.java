package com.ttpl.service.impl;

import com.ttpl.client.StudentServiceWebClient;
import com.ttpl.common.ApiResponse;
import com.ttpl.dto.request.CollegeRequestDto;
import com.ttpl.dto.response.CollegeResponseDto;
import com.ttpl.dto.response.StudentResponseDto;
import com.ttpl.entity.College;
import com.ttpl.repository.CollegeRepository;
import com.ttpl.service.CollegeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollegeServiceImpl implements CollegeService {

    private final CollegeRepository collegeRepository;
    private final ModelMapper modelMapper;
    //    private final StudentServiceClient studentServiceClient;
    private final StudentServiceWebClient studentServiceWebClient;

    @Override
    public ResponseEntity<?> createOrUpdateCollege(CollegeRequestDto dto) {
        College college = new College();
        if (dto.getId() == null) {

            College byClgCode = collegeRepository.findByClgCode(dto.getClgCode());

            if (byClgCode != null) {
                return ResponseEntity.ok(new ApiResponse<>("Success", HttpStatus.OK.value(), "College code already exist", null));
            }
            modelMapper.map(dto, college);

            College saved = collegeRepository.save(college);
            CollegeResponseDto response = modelMapper.map(saved, CollegeResponseDto.class);

            return ResponseEntity.ok(
                    new ApiResponse<>("success", HttpStatus.OK.value(), "College created successfully!", response)
            );
        }

        var existingCollege = collegeRepository.findById(dto.getId());
        if (existingCollege.isPresent()) {

            modelMapper.map(dto, college);

            College saved = collegeRepository.save(college);
            CollegeResponseDto response = modelMapper.map(saved, CollegeResponseDto.class);

            return ResponseEntity.ok(
                    new ApiResponse<>("success", HttpStatus.OK.value(), "College updated successfully!", response)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("error", HttpStatus.NOT_FOUND.value(), "College not found!", null));
        }
    }

    @Override
    public ResponseEntity<?> getCollegeById(Long id) {
        Optional<College> optionalCollege = collegeRepository.findById(id);

        if (optionalCollege.isEmpty()) {
            return ResponseEntity.ok(
                    new ApiResponse<>("Error", HttpStatus.NOT_FOUND.value(), "College not found!", null)
            );
        }

        College college = optionalCollege.get();

        ResponseEntity<ApiResponse<List<StudentResponseDto>>> responseEntity =
                studentServiceWebClient.getStudentsByClgCode(college.getClgCode()).block();

        ApiResponse<List<StudentResponseDto>> studentApiResponse = responseEntity.getBody();
        List<StudentResponseDto> students = null;

        if (studentApiResponse != null) {
            students = studentApiResponse.getData();
        }

        if (students != null && !students.isEmpty()) {
            college.setStudents(students);
        }

        CollegeResponseDto responseDto = modelMapper.map(college, CollegeResponseDto.class);

        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), "College found successfully!", responseDto)
        );
    }


    @Override
    public ResponseEntity<?> getAllCollege() {
        List<College> colleges = collegeRepository.findAll();

        if (colleges.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Error", HttpStatus.NOT_FOUND.value(), "No colleges found!", null));
        }

        List<CollegeResponseDto> responseList = colleges.stream()
                .map(college -> modelMapper.map(college, CollegeResponseDto.class))
                .toList();

        for (CollegeResponseDto college : responseList) {
            try {
                ResponseEntity<ApiResponse<List<StudentResponseDto>>> studentsByClgCode =
                        studentServiceWebClient.getStudentsByClgCode(college.getClgCode()).block();

                if (studentsByClgCode != null && studentsByClgCode.getBody() != null) {
                    List<StudentResponseDto> data = studentsByClgCode.getBody().getData();
                    if (data != null && !data.isEmpty()) {
                        List<StudentResponseDto> list = data.stream()
                                .map(student -> modelMapper.map(student, StudentResponseDto.class))
                                .toList();
                        college.setStudents(list);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error fetching students for college " + college.getClgCode() + ": " + e.getMessage());
            }
        }

        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), "Colleges fetched successfully!", responseList)
        );
    }


    @Override
    public ResponseEntity<ApiResponse<CollegeResponseDto>> getByCollegeCode(String code) {
        College college = collegeRepository.findByClgCode(code);

        if (college == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Error", HttpStatus.NOT_FOUND.value(), "Invalid college code!", null));
        }

        ResponseEntity<ApiResponse<List<StudentResponseDto>>> studentsByClgCode = null;
        try {
            studentsByClgCode = studentServiceWebClient.getStudentsByClgCode(code).block();
        } catch (Exception e) {
            System.out.println("Error fetching students for college: " + e.getMessage());
        }

        if (studentsByClgCode != null && studentsByClgCode.getBody() != null) {
            List<StudentResponseDto> data = studentsByClgCode.getBody().getData();
            if (data != null) {
                List<StudentResponseDto> list = data.stream()
                        .map(student -> modelMapper.map(student, StudentResponseDto.class))
                        .toList();
                college.setStudents(list);
            }
        }

        CollegeResponseDto response = modelMapper.map(college, CollegeResponseDto.class);

        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), "College found successfully!", response)
        );
    }


    @Override
    public ResponseEntity<?> deleteById(Long id) {
        Optional<College> college = collegeRepository.findById(id);

        if (college.isPresent()) {

            ResponseEntity<ApiResponse<List<StudentResponseDto>>> studentsByClgCode = studentServiceWebClient.getStudentsByClgCode(college.get().getClgCode()).block();
            List<StudentResponseDto> students = studentsByClgCode.getBody().getData();

            for (var s : students) {
                studentServiceWebClient.deleteByClgCode(s.getClgCode());
            }

            collegeRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>("Success", HttpStatus.OK.value(), "College deleted Successfully!", null));
        }


        return ResponseEntity.ok(new ApiResponse<>("Success", HttpStatus.NOT_FOUND.value(), "College not found!", null));
    }

    @Override
    public ResponseEntity<?> deleteByClgCode(String code) {
        College byClgCode = collegeRepository.findByClgCode(code);

        if (byClgCode == null) {
            return ResponseEntity.ok(new ApiResponse<>("Success", HttpStatus.NOT_FOUND.value(), "College not found!", null));
        }

        ResponseEntity<ApiResponse<List<StudentResponseDto>>> studentsByClgCode = studentServiceWebClient.getStudentsByClgCode(code).block();
        List<StudentResponseDto> students = studentsByClgCode.getBody().getData();

        if (!students.isEmpty()) {
            for (var s : students) {
                studentServiceWebClient.deleteByClgCode(s.getClgCode());
            }
        }
        collegeRepository.delete(byClgCode);
        return ResponseEntity.ok(new ApiResponse<>("Success", HttpStatus.OK.value(), "College deleted successfully!", null));
    }

}