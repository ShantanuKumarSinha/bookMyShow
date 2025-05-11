package com.shann.bookmyshow.controllers;

import com.shann.bookmyshow.dtos.BookingRequestDto;
import com.shann.bookmyshow.dtos.BookingResponseDto;
import com.shann.bookmyshow.dtos.ResponseStatusDto;
import com.shann.bookmyshow.exceptions.ShowNotFoundException;
import com.shann.bookmyshow.exceptions.UserNotFoundException;
import com.shann.bookmyshow.services.BookingService;
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
    public BookingResponseDto bookTicket(@RequestBody BookingRequestDto bookingRequestDTO) throws UserNotFoundException, ShowNotFoundException {
        // Call the booking service to process the booking
        var responseDTO = new BookingResponseDto();
        try {
            var booking = bookingService.bookTicket(bookingRequestDTO.getUserId(), bookingRequestDTO.getShowId(), bookingRequestDTO.getShowSeatIds());
            responseDTO.setTicket(booking);
            responseDTO.setResponseDTO(ResponseStatusDto.SUCCESS);
        } catch (UserNotFoundException | ShowNotFoundException e) {
            responseDTO.setResponseDTO(ResponseStatusDto.FAILURE);
        }
        // Return the response DTO
        return responseDTO;
    }
}
