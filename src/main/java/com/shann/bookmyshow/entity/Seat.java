package com.shann.bookmyshow.entity;

import com.shann.bookmyshow.enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Seat extends BaseModel {
    private String seatNumber;
    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;
    @ManyToOne
    //@JoinColumn(name = "id", nullable = false)
    private Screen screen;


}
