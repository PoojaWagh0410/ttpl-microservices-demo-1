package com.ttpl.controller;

import com.ttpl.dto.request.CollegeRequestDto;
import com.ttpl.service.CollegeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/college")
@RequiredArgsConstructor
@Slf4j
public class CollegeController {

    private final CollegeService collegeService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity createOrUpdateCollege(@RequestBody @Valid CollegeRequestDto dto){
        return collegeService.createOrUpdateCollege(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCollegeById(@PathVariable Long id){
        return collegeService.getCollegeById(id);
    }

    @GetMapping("/all")
    public ResponseEntity getAllColleges(){
        return collegeService.getAllCollege();
    }

    @GetMapping("/clgCode/{code}")
    public ResponseEntity getByCollegeCode(@PathVariable String code){
        return collegeService.getByCollegeCode(code);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteByClgId(@PathVariable Long id){
        return collegeService.deleteById(id);
    }

    @DeleteMapping("/clgCode/{code}")
    public ResponseEntity deleteByClgCode(@PathVariable String code){
        return collegeService.deleteByClgCode(code);
    }


}