package com.shann.bookmyshow.dtos;

import com.shann.bookmyshow.entities.Booking;
import lombok.Data;

@Data
public class BookingResponseDto {
    private Booking ticket;
    private ResponseStatusDto responseDTO;

}
