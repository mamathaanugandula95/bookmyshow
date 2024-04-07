package com.scaler.bookmyshow.repositories;

import com.scaler.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    @Override
    List<ShowSeat> findAllById(Iterable<Long> longs);

    @Override
    ShowSeat save(ShowSeat showSeat);
    // 2 ways -> Insert + Update.
    // INSERT => If the input showSeat object doesn't have the ID value then it will be like
    // insert call and it will return a showSeat object with ID.

    // UPDATE => If the input showSeat object has already ab ID value, then it will be an
    //update call and it will return a updated showSeat object.
}