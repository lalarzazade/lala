package com.example.secondexamsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SecondExamRegisterRequest {

    private String email;
    private String password;
}