package com.scaler.bookmyshow.services;


import com.scaler.bookmyshow.exceptions.InvalidShowException;
import com.scaler.bookmyshow.exceptions.InvalidUserException;
import com.scaler.bookmyshow.exceptions.ShowSeatNotAvailableException;
import com.scaler.bookmyshow.models.*;
import com.scaler.bookmyshow.repositories.ShowRepository;
import com.scaler.bookmyshow.repositories.ShowSeatRepository;
import com.scaler.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculator priceCalculator;

    @Autowired
    BookingService(UserRepository userRepository, ShowRepository showRepository,
                   ShowSeatRepository showSeatRepository, PriceCalculator priceCalculator) {
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculator = priceCalculator;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE) // Approach 1
    public Booking bookMovie(Long userId, Long showId, List<Long> showSeatIds) throws InvalidUserException, InvalidShowException, ShowSeatNotAvailableException {
        //STEPS:
        // ------- TAKE A LOCK (Approach 1) -------
        //1. Get user with the userId.
        //2. Get show with the showId.
        //3. Get showSeats with showSeatIds.
        // ------- TAKE A LOCK (Approach 2) -----------
        //4. Check if seats are available or not.
        //5. if no, throw an exception.
        //6. If yes, Mark the seat status as BLOCKED.
        //7. Save the updated status in DB.
        // --------- RELEASE THE LOCK (Approach 2) --------
        //8. Create the booking object with PENDING status.
        //9. Return the booking object.
        // ------- RELEASE THE LOCK (Approach 1) -------

        //1. Get user with the userId.
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new InvalidUserException("Invalid User");
        }

        User user = optionalUser.get();

        //2. Get show with the showId.
        Optional<Show> optionalShow = showRepository.findById(showId);

        if (optionalShow.isEmpty()) {
            throw new InvalidShowException("Invalid Show");
        }

        Show show = optionalShow.get();

        //3. Get showSeats with showSeatIds.
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        //select * from show_seat where id IN (1, 2, 3, 4);

        //4. Check if seats are available or not.
        for (ShowSeat showSeat : showSeats) {
            if (!showSeat.getSeatStatus().equals(SeatStatus.AVAILABLE)) {
                //5. if no, throw an exception.
                throw new ShowSeatNotAvailableException("ShowSeat not available");
            }
        }

        List<ShowSeat> finalShowSeats = new ArrayList<>();

        //6. If yes, Mark the seat status as BLOCKED.
        for (ShowSeat showSeat : showSeats) {
            showSeat.setSeatStatus(SeatStatus.BLOCKED);
            //7. Save the updated status in DB.
            finalShowSeats.add(showSeatRepository.save(showSeat));
        }

        //8. Create the Booking object.
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setTimeOfBooking(new Date());
        booking.setShow(show);
        booking.setUser(user);
        booking.setSeats(finalShowSeats);
        booking.setPayments(new ArrayList<>());
        booking.setPrice(priceCalculator.calculatePrice(show, finalShowSeats));

        return booking;
    }
}