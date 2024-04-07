package com.scaler.bookmyshow;

import com.scaler.bookmyshow.controllers.UserController;
import com.scaler.bookmyshow.dtos.SignUpRequestDto;
import com.scaler.bookmyshow.dtos.SignUpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing// This annotation is mostly used in configuration or SpringBootApplication class like this
// The main purpose of this annotation in this code is to actively modify the dates.
@SpringBootApplication
public class BookMyShowApplication implements CommandLineRunner {
    @Autowired
    private UserController userController;

    public static void main(String[] args) {

        SpringApplication.run(BookMyShowApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        SignUpRequestDto requestDto = new SignUpRequestDto();
        requestDto.setEmailId("mamathaanugandula95@gmail.com");
        requestDto.setPassword("mamatha");
        SignUpResponseDto responseDto =userController.singUp(requestDto);

    }
}