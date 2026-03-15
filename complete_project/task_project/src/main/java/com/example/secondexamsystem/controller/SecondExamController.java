package com.example.secondexamsystem.controller;

import com.example.secondexamsystem.dto.SecondExamRegisterRequest;
import com.example.secondexamsystem.service.SecondExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secondExam")
@RequiredArgsConstructor
public class SecondExamController {

    private final SecondExamService secondExamService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SecondExamRegisterRequest request) {
        String result = secondExamService.registerForSecondExam(request);

        return switch (result) {
            case "Yanlış email və ya şifrə" -> ResponseEntity.status(401).body(result);
            case "Birinci imtahan məlumatı tapılmadı" -> ResponseEntity.status(404).body(result);
            case "Tələbə birinci imtahandan keçməyib" -> ResponseEntity.status(403).body(result);
            case "İkinci imtahan üçün artıq qeydiyyat mövcuddur" -> ResponseEntity.status(409).body(result);
            default -> ResponseEntity.ok(result);
        };
    }
}
