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
    // price has been moved to ShowSeatType
    //private Double price;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "show_seat_type_id", referencedColumnName = "id")
    private ShowSeatType showSeatType;
}
