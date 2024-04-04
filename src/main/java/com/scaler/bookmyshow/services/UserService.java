package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.exceptions.InvalidUserException;
import com.scaler.bookmyshow.models.User;
import com.scaler.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User signUp(String emailId, String password) throws InvalidUserException {
        Optional<User> optionalUser = userRepository.findByEmail(emailId);

        //If the user is present in the DB then go to login workflow
        if (optionalUser.isPresent()) {
            login(emailId, password);
        }

        User user = new User();
        user.setBookings(new ArrayList<>());
        user.setEmail(emailId);

        //Before we store the password to DB we should encrypt it using BCryptEncoder.
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));

        //save the user to DB.
        return userRepository.save(user);
    }

    public boolean login(String emailId, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(emailId);

        String passwordStoredInDB = optionalUser.get().getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(password, passwordStoredInDB);
    }
}
