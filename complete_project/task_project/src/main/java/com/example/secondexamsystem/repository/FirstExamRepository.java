package com.example.secondexamsystem.repository;

import com.example.secondexamsystem.entity.FirstExamEntity;
import com.example.secondexamsystem.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FirstExamRepository extends JpaRepository<FirstExamEntity, Long> {
    Optional<FirstExamEntity> findByStudent(StudentEntity student);
}