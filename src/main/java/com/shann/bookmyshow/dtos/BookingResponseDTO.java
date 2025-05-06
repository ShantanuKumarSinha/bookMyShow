package com.shann.bookmyshow.dtos;

import com.shann.bookmyshow.entities.Booking;
import lombok.Data;

@Data
public class BookingResponseDTO {
    private Booking ticket;
    private ResponseStatusDTO responseDTO;

}
