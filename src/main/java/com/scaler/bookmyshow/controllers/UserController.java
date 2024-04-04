package com.scaler.bookmyshow.controllers;

import com.scaler.bookmyshow.dtos.SignUpRequestDto;
import com.scaler.bookmyshow.dtos.SignUpResponseDto;
import com.scaler.bookmyshow.models.ResponseStatus;
import com.scaler.bookmyshow.models.User;
import com.scaler.bookmyshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    public SignUpResponseDto singUp(SignUpRequestDto requeustDto) {
        User user;
        SignUpResponseDto responseDto = new SignUpResponseDto();

        try {
            user = userService.signUp(requeustDto.getEmailId(), requeustDto.getPassword());

            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setUserId(user.getId());
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            responseDto.setUserId(-1L);
        }

        return responseDto;
    }
}