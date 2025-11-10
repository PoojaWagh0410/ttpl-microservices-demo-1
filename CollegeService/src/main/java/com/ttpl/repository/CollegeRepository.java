package com.ttpl.repository;

import com.ttpl.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {

    College findByClgCode(String code);

    void deleteByClgCode(String code);

}
