package com.scaler.bookmyshow.controllers;

import com.scaler.bookmyshow.dtos.BookMovieRequestDto;
import com.scaler.bookmyshow.dtos.BookMovieResponseDto;
import com.scaler.bookmyshow.models.Booking;
import com.scaler.bookmyshow.models.ResponseStatus;
import com.scaler.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private BookingService bookingService;

    @Autowired
    BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    //Dependency Injection -> Spring is a Dependency Injection framework.

    BookMovieResponseDto bookMovie(BookMovieRequestDto requestDto) {
        //DTO -> Data Transfer Objects
        BookMovieResponseDto responseDto = new BookMovieResponseDto();

        try {
            Booking booking = bookingService.bookMovie(
                    requestDto.getUserId(),
                    requestDto.getShowId(),
                    requestDto.getShowSeatId()
            );

            responseDto.setBookingId(booking.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setAmount(booking.getPrice());
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}