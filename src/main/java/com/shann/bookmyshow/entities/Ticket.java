package com.shann.bookmyshow.entities;

import com.shann.bookmyshow.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Ticket extends BaseModel {
    private String bookingNumber;
    @ManyToOne
    private User user;
    @ManyToMany // seat can be part of canceled booking so @mManyToMany is more appropriate
    private List<ShowSeat> seats;
    @Enumerated(EnumType.ORDINAL)
    private TicketStatus ticketStatus;
    private double amount;
    @OneToMany
    private List<Payment> payments;


}
