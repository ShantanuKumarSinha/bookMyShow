package com.shann.bookmyshow.entities;

import com.shann.bookmyshow.enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
public class Seat extends BaseModel {
    private String name;
    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;
    @ManyToOne
    @JoinColumn(name = "screen_id", referencedColumnName = "id")
    private Screen screen;


}
