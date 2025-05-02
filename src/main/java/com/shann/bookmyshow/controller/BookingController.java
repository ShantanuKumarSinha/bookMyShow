package com.shann.bookmyshow.controller;

import com.shann.bookmyshow.dto.BookingRequestDTO;
import com.shann.bookmyshow.dto.BookingResponseDTO;
import com.shann.bookmyshow.dto.ResponseStatusDTO;
import com.shann.bookmyshow.exceptions.ShowNotFoundException;
import com.shann.bookmyshow.exceptions.UserNotFoundException;
import com.shann.bookmyshow.service.BookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("")
    public BookingResponseDTO bookTicket(@RequestBody BookingRequestDTO bookingRequestDTO) throws UserNotFoundException, ShowNotFoundException {
        // Call the booking service to process the booking
        var responseDTO = new BookingResponseDTO();
        try {
            var booking = bookingService.bookTicket(bookingRequestDTO.getUserId(), bookingRequestDTO.getShowId(), bookingRequestDTO.getShowSeatIds());
            responseDTO.setTicket(booking);
            responseDTO.setResponseDTO(ResponseStatusDTO.SUCCESS);
        } catch (UserNotFoundException | ShowNotFoundException e) {
            responseDTO.setResponseDTO(ResponseStatusDTO.FAILURE);
        }
        // Return the response DTO
        return responseDTO;
    }
}
