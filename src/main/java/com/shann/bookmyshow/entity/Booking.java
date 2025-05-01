package com.shann.bookmyshow.entity;

import com.shann.bookmyshow.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Booking extends BaseModel {
    private String bookingNumber;
    @ManyToOne
    private User user;
    @ManyToMany // seat can be part of canceled booking so @mManyToMany is more appropriate
    private List<ShowSeat> seats;
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;
    private double amount;
    @OneToMany
    private List<Payment> payments;


}
