package com.example.secondexamsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EntityScan(basePackages = {"entity"})
//@EnableJpaRepositories(basePackages = {"repository"})
public class SecondExamSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondExamSystemApplication.class, args);
    }
}
