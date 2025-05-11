package com.shann.bookmyshow.entities;

import com.shann.bookmyshow.enums.ShowSeatStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ShowSeat extends BaseModel {
    @ManyToOne
    private Show show;
    @ManyToOne
    private Seat seat;
    @Enumerated(EnumType.ORDINAL)
    private ShowSeatStatus showSeatStaus;
    // price has been moved to SeatTypeShow
    //private Double price;
    @OneToOne
    @JoinColumn(name = "seat_type_show_id", referencedColumnName = "id")
    private SeatTypeShow seatTypeShow;
}
