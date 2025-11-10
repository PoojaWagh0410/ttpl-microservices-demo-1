package com.ttpl.repository;

import com.ttpl.entity.Student;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    Optional<Student> findByEmail(String email);

    List<Student> findAllByClgCode(String clgCode);

    Optional<Student> findByPhone(@NotNull(message = "Phone number cannot be null") @Pattern(regexp = "^[0-9]{10}$", message = "Please enter a valid 10-digit phone number") String phone);
}
