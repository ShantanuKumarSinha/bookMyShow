package com.shann.bookmyshow.entity;

import com.shann.bookmyshow.enums.ShowSeatStaus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ShowSeat extends BaseModel {
    @ManyToOne
    private Show show;
    @ManyToOne
    private Seat seat;
    @Enumerated(EnumType.ORDINAL)
    private ShowSeatStaus showSeatStaus;
    private Double price;
}
