package com.shann.bookmyshow.entity;

import com.shann.bookmyshow.enums.SeatType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Seat extends BaseModel {
    private String seatNumber;
    private SeatType seatType;
    @ManyToOne
    //@JoinColumn(name = "id", nullable = false)
    private Screen screen;


}
