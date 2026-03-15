package com.example.secondexamsystem.service.impl;

import com.example.secondexamsystem.dto.SecondExamRegisterRequest;
import com.example.secondexamsystem.entity.FirstExamEntity;
import com.example.secondexamsystem.entity.SecondExamEntity;
import com.example.secondexamsystem.entity.StudentEntity;
import com.example.secondexamsystem.service.SecondExamService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.secondexamsystem.repository.FirstExamRepository;
import com.example.secondexamsystem.repository.SecondExamRepository;
import com.example.secondexamsystem.repository.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecondExamServiceImpl implements SecondExamService {

    private final StudentRepository studentRepository;
    private final FirstExamRepository firstExamRepository;
    private final SecondExamRepository secondExamRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.qr-storage-path}")
    private String qrStoragePath;

    @Override
    public String registerForSecondExam(SecondExamRegisterRequest request) {

        Optional<StudentEntity> studentOptional = studentRepository.findByEmail(request.getEmail());

        if (studentOptional.isEmpty()) {
            return "Yanlış email və ya şifrə";
        }

        StudentEntity student = studentOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), student.getPassword())) {
        }

        Optional<FirstExamEntity> firstExamOptional = firstExamRepository.findByStudent(student);

        if (firstExamOptional.isEmpty()) {
            return "Birinci imtahan məlumatı tapılmadı";
        }

        FirstExamEntity firstExam = firstExamOptional.get();

        if (!firstExam.getPassed()) {
            return "Tələbə birinci imtahandan keçməyib";
        }

        Optional<SecondExamEntity> existingSecondExam = secondExamRepository.findByStudent(student);

        if (existingSecondExam.isPresent()) {
            return "İkinci imtahan üçün artıq qeydiyyat mövcuddur";
        }

        String qrContent = String.format(
                "Tələbə: %s %s | Email: %s | Token: %s",
                student.getName(), student.getSurname(), student.getEmail(), UUID.randomUUID()
        );

        String fileName = "qr_" + student.getId() + ".png";
        String qrUrl = baseUrl + "/qr/" + fileName;

        saveQrToFile(qrContent, fileName);

        firstExam.setSecondExamQrUrl(qrUrl);
        firstExamRepository.save(firstExam);

        SecondExamEntity secondExam = new SecondExamEntity();
        secondExam.setStudent(student);
        secondExamRepository.save(secondExam);

        return "İkinci imtahan üçün qeydiyyat uğurla tamamlandı. QR URL: " + qrUrl;
    }

    private void saveQrToFile(String content, String fileName) {
        try {
            Path dirPath = Paths.get(qrStoragePath);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300);
            Path filePath = dirPath.resolve(fileName);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("QR kod saxlanılarkən xəta: " + e.getMessage());
        }
    }
}
