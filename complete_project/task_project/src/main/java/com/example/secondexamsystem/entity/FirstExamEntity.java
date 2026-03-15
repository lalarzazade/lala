package com.example.secondexamsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "first_exam")
public class FirstExamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private StudentEntity student;

    @Column(nullable = false)
    private Double result;

    @Column(nullable = false)
    private Boolean passed;

    @Column(name = "second_exam_qr_url")
    private String secondExamQrUrl;

    public FirstExamEntity() {
    }

    public Long getId() {
        return id;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public Double getResult() {
        return result;
    }

    public Boolean getPassed() {
        return passed;
    }

    public String getSecondExamQrUrl() {
        return secondExamQrUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public void setSecondExamQrUrl(String secondExamQrUrl) {
        this.secondExamQrUrl = secondExamQrUrl;
    }
}