package com.scaler.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "shows")
public class Show extends BaseModel{
    @ManyToOne
    private Movie movie;
    private String language;
    private Date startTime;
    private Date endTime;
    @ManyToOne
    private Screen screen;
}