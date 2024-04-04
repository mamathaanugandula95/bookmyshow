package com.scaler.bookmyshow.repositories;

import com.scaler.bookmyshow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long userId); // finding the user using the userId. No need to write whole SQL query

    Optional<User> findByEmail(String email);
    @Override
    User save(User user);
}

/*
To create any repository follow 2 steps :-
1. Make the Repository as an Interface.
2. Make the Repository Interface extends the JPARepository interface.
*/