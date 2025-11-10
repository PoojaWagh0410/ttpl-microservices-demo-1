package com.ttpl.service.impl;

import com.ttpl.client.CollegeServiceWebClient;
import com.ttpl.common.ApiResponse;
import com.ttpl.dto.request.StudentRequestDto;
import com.ttpl.dto.response.CollegeResponseDto;
import com.ttpl.dto.response.StudentResponseDto;
import com.ttpl.entity.Student;
import com.ttpl.repository.StudentRepository;
import com.ttpl.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final CollegeServiceWebClient collegeServiceWebClient;

    @Override
    public ResponseEntity<?> createOrUpdateStudent(StudentRequestDto dto) {

        if (dto.getId() == null) {
            Optional<Student> existing = studentRepository.findByEmail(dto.getEmail());
            if (existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ApiResponse<>("Error", HttpStatus.CONFLICT.value(),
                                "Student with this email already exists!", null));
            }

            Optional<Student> byPhone = studentRepository.findByPhone(dto.getPhone());
            if(byPhone.isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ApiResponse<>("Error", HttpStatus.CONFLICT.value(),
                                "Student with this phone number is already exists!", null));
            }

            ResponseEntity<ApiResponse<CollegeResponseDto>> byClgCode =
                    collegeServiceWebClient.getByCollegeCode(dto.getClgCode()).block();

            CollegeResponseDto data = byClgCode.getBody().getData();
            if (data == null) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>("Error", HttpStatus.BAD_REQUEST.value(),
                                "Invalid college code : " + dto.getClgCode(), null));
            }

            Student student = modelMapper.map(dto, Student.class);
            Student saved = studentRepository.save(student);
            StudentResponseDto response = modelMapper.map(saved, StudentResponseDto.class);

            return ResponseEntity.ok(
                    new ApiResponse<>("Success", HttpStatus.OK.value(),
                            "Student created successfully!", response)
            );
        }

        Optional<Student> existingStudent = studentRepository.findById(dto.getId());
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            modelMapper.map(dto, student);

            Student updated = studentRepository.save(student);
            StudentResponseDto response = modelMapper.map(updated, StudentResponseDto.class);

            return ResponseEntity.ok(
                    new ApiResponse<>("Success", HttpStatus.OK.value(),
                            "Student updated successfully!", response)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Error", HttpStatus.NOT_FOUND.value(),
                            "Student not found!", null));
        }
    }

    @Override
    public ResponseEntity<?> getStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Error", HttpStatus.NOT_FOUND.value(), "Student not found!", null));
        }

        Student student = optionalStudent.get();

        ResponseEntity<ApiResponse<CollegeResponseDto>> responseEntity =
                collegeServiceWebClient.getByCollegeCode(student.getClgCode()).block();

        CollegeResponseDto collegeData = null;
        if (responseEntity != null && responseEntity.getBody() != null) {
            collegeData = responseEntity.getBody().getData();
        }

        StudentResponseDto responseDto = modelMapper.map(student, StudentResponseDto.class);

        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), "Student found successfully!", responseDto)
        );
    }

    @Override
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Error", HttpStatus.NOT_FOUND.value(), "No students found!", null));
        }

        List<StudentResponseDto> responseList = students.stream()
                .map(student -> {
                    StudentResponseDto dto = modelMapper.map(student, StudentResponseDto.class);

                    return dto;
                })
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), "Students fetched successfully!", responseList)
        );
    }

    @Override
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> getStudentsByCollegeCode(String clgCode) {
        List<Student> students = studentRepository.findAllByClgCode(clgCode);

        if (students.isEmpty()) {
            return ResponseEntity.ok(
                    new ApiResponse<>("Success", HttpStatus.OK.value(), "No students found for this college!", List.of())
            );
        }

        List<StudentResponseDto> studentDtos = students.stream()
                .map(student -> modelMapper.map(student, StudentResponseDto.class))
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), "Students found successfully!", studentDtos)
        );
    }


    @Override
    public ResponseEntity<?> deleteById(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            studentRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>("Success", HttpStatus.OK.value(), "Student deleted successfully!", null));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("Error", HttpStatus.NOT_FOUND.value(), "Student not found!", null));
    }

    @Override
    public ResponseEntity<?> deleteByClgCode(String clgCode) {
        List<Student> students = studentRepository.findAllByClgCode(clgCode);

        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Error", HttpStatus.NOT_FOUND.value(), "No students found for this college!", null));
        }

        students.stream().forEach(studentRepository::delete);

        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), "All students of this college deleted successfully!", null)
        );
    }
}
