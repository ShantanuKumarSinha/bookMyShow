package com.shann.bookmyshow.entities;

import com.shann.bookmyshow.enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SeatTypeShow extends BaseModel {
    @ManyToOne
    private Show show;
    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;
    private double price;
}
