package com.scaler.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
@Getter
@Setter
@Entity
public class ShowSeat extends BaseModel{
    // 1 -> 1
    // m <- 1
    // m->1
    @ManyToOne
    private Show show;

    // 1 -> 1
    // m <- 1
    // m:1
    @ManyToOne
    private Seat seat;

    @Enumerated(EnumType.ORDINAL)
    private SeatStatus seatStatus;
}