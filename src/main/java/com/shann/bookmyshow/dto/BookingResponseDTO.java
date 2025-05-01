package com.shann.bookmyshow.dto;

import com.shann.bookmyshow.entity.Booking;
import lombok.Data;

@Data
public class BookingResponseDTO {
    private Booking ticket;
    private ResponseStatusDTO responseDTO;

}
