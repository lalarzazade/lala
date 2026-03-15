package com.example.secondexamsystem.repository;

import com.example.secondexamsystem.entity.SecondExamEntity;
import com.example.secondexamsystem.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecondExamRepository extends JpaRepository<SecondExamEntity, Long> {
    Optional<SecondExamEntity> findByStudent(StudentEntity student);
}
