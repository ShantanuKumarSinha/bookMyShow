package com.shann.bookmyshow.dtos;

import com.shann.bookmyshow.entities.Ticket;
import lombok.Data;

@Data
public class BookingResponseDto {
    private Ticket ticket;
    private ResponseStatus responseStatus;

}
