package com.shann.bookmyshow.controllers;

import com.shann.bookmyshow.dtos.BookingRequestDto;
import com.shann.bookmyshow.dtos.BookingResponseDto;
import com.shann.bookmyshow.dtos.ResponseStatus;
import com.shann.bookmyshow.exceptions.ShowNotFoundException;
import com.shann.bookmyshow.exceptions.UserNotFoundException;
import com.shann.bookmyshow.services.TicketService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling ticket booking requests.
 */
@RestController
@RequestMapping("/bookings")
public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Endpoint to book a ticket.
     *
     * @param bookingRequestDTO the booking request DTO containing user ID, show ID, and seat IDs
     * @return the booking response DTO containing ticket details and response status
     * @throws UserNotFoundException if the user is not found
     * @throws ShowNotFoundException if the show is not found
     */
    @PostMapping("")
    public BookingResponseDto bookTicket(@RequestBody BookingRequestDto bookingRequestDTO) throws UserNotFoundException, ShowNotFoundException {
        // Call the booking service to process the booking
        var responseDTO = new BookingResponseDto();
        try {
            var ticket = ticketService.bookTicket(bookingRequestDTO.getUserId(), bookingRequestDTO.getShowId(), bookingRequestDTO.getShowSeatIds());
            responseDTO.setTicket(ticket);
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (UserNotFoundException | ShowNotFoundException e) {
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        // Return the response DTO
        return responseDTO;
    }
}
