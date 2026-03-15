package com.example.secondexamsystem.repository;

import com.example.secondexamsystem.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
        Optional<StudentEntity> findByEmail(String email);
}

