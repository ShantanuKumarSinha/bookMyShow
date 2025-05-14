package com.shann.bookmyshow.controllers;

import com.shann.bookmyshow.dtos.BookingRequestDto;
import com.shann.bookmyshow.dtos.BookingResponseDto;
import com.shann.bookmyshow.dtos.ResponseStatus;
import com.shann.bookmyshow.entities.*;
import com.shann.bookmyshow.enums.SeatType;
import com.shann.bookmyshow.enums.ShowSeatStatus;
import com.shann.bookmyshow.exceptions.ShowNotFoundException;
import com.shann.bookmyshow.exceptions.UserNotFoundException;
import com.shann.bookmyshow.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@Profile("test")
public class TestTicketController {

    @Autowired
    private TicketController ticketController;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowSeatTypeRepository showSeatTypeRepository;

    private User user;
    private List<ShowSeat> showSeats;

    @BeforeEach
    public void insertDummyData() {
        User u = new User();
        u.setUsername("Test User");
        u.setEmail("test@scaler.com");
        user = userRepository.save(u);

        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("Test Movie");
        movie.setDescription("Test Description");

        Show show = new Show();
//        show.setMovie(movie);
        show.setStartTime(new Date());
        show.setEndTime(new Date());
        show = showRepository.save(show);

        Seat seat1 = new Seat();
        seat1.setName("1A");
        seat1.setSeatType(SeatType.GOLD);
        seat1 = seatRepository.save(seat1);

        Seat seat2 = new Seat();
        seat2.setName("1B");
        seat2.setSeatType(SeatType.GOLD);
        seat2 = seatRepository.save(seat2);

        Seat seat3 = new Seat();
        seat3.setName("2A");
        seat3.setSeatType(SeatType.SILVER);
        seat3 = seatRepository.save(seat3);

        Seat seat4 = new Seat();
        seat4.setName("2B");
        seat4.setSeatType(SeatType.SILVER);
        seat4 = seatRepository.save(seat4);

        ShowSeatType showSeatType1 = new ShowSeatType();
        showSeatType1.setSeatType(seat1.getSeatType());
        showSeatType1.setPrice(200.0);

        ShowSeatType showSeatType2 = new ShowSeatType();
        showSeatType2.setSeatType(seat2.getSeatType());
        showSeatType2.setPrice(200.0);
        ShowSeatType showSeatType3 = new ShowSeatType();
        showSeatType3.setSeatType(seat3.getSeatType());
        showSeatType3.setPrice(100.0);
        ShowSeatType showSeatType4 = new ShowSeatType();
        showSeatType4.setSeatType(seat4.getSeatType());
        showSeatType4.setPrice(100.0);
        var showSeatTypes = showSeatTypeRepository.saveAll(List.of(showSeatType1, showSeatType2, showSeatType3, showSeatType4));

        ShowSeat showSeat1 = new ShowSeat();
        showSeat1.setSeat(seat1);
        showSeat1.setShow(show);
        showSeat1.setShowSeatStaus(ShowSeatStatus.AVAILABLE);
        showSeat1.setShowSeatType(showSeatType1);
        showSeat1 = showSeatRepository.save(showSeat1);

        ShowSeat showSeat2 = new ShowSeat();
        showSeat2.setSeat(seat2);
        showSeat2.setShow(show);
        showSeat2.setShowSeatStaus(ShowSeatStatus.AVAILABLE);
        showSeat2.setShowSeatType(showSeatType2);
        showSeat2 = showSeatRepository.save(showSeat2);

        ShowSeat showSeat3 = new ShowSeat();
        showSeat3.setSeat(seat3);
        showSeat3.setShow(show);
        showSeat3.setShowSeatStaus(ShowSeatStatus.AVAILABLE);
        showSeat3.setShowSeatType(showSeatType3);
        showSeat3 = showSeatRepository.save(showSeat3);

        ShowSeat showSeat4 = new ShowSeat();
        showSeat4.setSeat(seat4);
        showSeat4.setShow(show);
        showSeat4.setShowSeatStaus(ShowSeatStatus.AVAILABLE);
        showSeat4.setShowSeatType(showSeatType4);
        showSeat4 = showSeatRepository.save(showSeat4);

        showSeats = List.of(showSeat1, showSeat2, showSeat3, showSeat4);
    }

    @AfterEach
    public void cleanUp() {
        ticketRepository.deleteAll();
        showSeatRepository.deleteAll();
        showRepository.deleteAll();
        seatRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testBookTicket_1_Request_Success() throws Exception {
        BookingRequestDto requestDTO = new BookingRequestDto();
        requestDTO.setShowSeatIds(List.of(showSeats.get(0).getId(), showSeats.get(1).getId()));
        requestDTO.setUserId(user.getId());
        requestDTO.setShowId(showSeats.get(0).getShow().getId());
        BookingResponseDto responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, responseDTO.getResponseStatus(), "Status should be SUCCESS");
        assertNotNull(responseDTO.getTicket(), "Ticket should not be null");
        assertNotNull(responseDTO.getTicket().getSeats(), "Seats should not be null");
        assertEquals(2, responseDTO.getTicket().getSeats().size(), "Seats should be 2");

        Ticket ticket = responseDTO.getTicket();
        int ticketId = ticket.getId();
        ticketRepository.findById(ticketId).orElseThrow(() -> new Exception("Ticket should be stored in database"));
        List<ShowSeat> showSeats = showSeatRepository.findAll();
        assertEquals(ShowSeatStatus.OCCUPIED, showSeats.get(0).getShowSeatStaus(), "Seat status should be OCCUPIED");
        assertEquals(ShowSeatStatus.OCCUPIED, showSeats.get(1).getShowSeatStaus(), "Seat status should be OCCUPIED");
        assertEquals(ShowSeatStatus.AVAILABLE, showSeats.get(2).getShowSeatStaus(), "Seat status should be AVAILABLE");
        assertEquals(ShowSeatStatus.AVAILABLE, showSeats.get(3).getShowSeatStaus(), "Seat status should be AVAILABLE");
    }

    @Test
    public void testBookTicket_2_Request_Success_Non_overlapping_seats() throws Exception {
        BookingRequestDto requestDTO = new BookingRequestDto();
        requestDTO.setShowSeatIds(List.of(showSeats.get(0).getId(), showSeats.get(1).getId()));
        requestDTO.setUserId(user.getId());
        requestDTO.setShowId(showSeats.get(0).getShow().getId());
        BookingResponseDto responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, responseDTO.getResponseStatus(), "Status should be SUCCESS");
        assertNotNull(responseDTO.getTicket(), "Ticket should not be null");
        assertNotNull(responseDTO.getTicket().getSeats(), "Seats should not be null");
        assertEquals(2, responseDTO.getTicket().getSeats().size(), "Seats should be 2");

        Ticket ticket = responseDTO.getTicket();
        int ticketId = ticket.getId();
        ticketRepository.findById(ticketId).orElseThrow(() -> new Exception("Ticket should be stored in database"));

        requestDTO = new BookingRequestDto();
        requestDTO.setShowSeatIds(List.of(showSeats.get(2).getId(), showSeats.get(3).getId()));
        requestDTO.setShowId(showSeats.get(2).getShow().getId());
        requestDTO.setUserId(user.getId());
        responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, responseDTO.getResponseStatus(), "Status should be SUCCESS");
        ticket = responseDTO.getTicket();
        ticketId = ticket.getId();
        ticketRepository.findById(ticketId).orElseThrow(() -> new Exception("Ticket should be stored in database"));

        assertNotNull(ticket, "Ticket should not be null");
        List<ShowSeat> showSeats = showSeatRepository.findAll();
        assertEquals(ShowSeatStatus.OCCUPIED, showSeats.get(0).getShowSeatStaus(), "Seat status should be OCCUPIED");
        assertEquals(ShowSeatStatus.OCCUPIED, showSeats.get(1).getShowSeatStaus(), "Seat status should be OCCUPIED");
        assertEquals(ShowSeatStatus.OCCUPIED, showSeats.get(2).getShowSeatStaus(), "Seat status should be OCCUPIED");
        assertEquals(ShowSeatStatus.OCCUPIED, showSeats.get(3).getShowSeatStaus(), "Seat status should be OCCUPIED");
    }

    @Test
    public void testBookTicket_NonExistingUser_Failure() throws UserNotFoundException, ShowNotFoundException {
        BookingRequestDto requestDTO = new BookingRequestDto();
        requestDTO.setShowSeatIds(List.of(showSeats.get(0).getId(), showSeats.get(1).getId()));
        requestDTO.setUserId(100);
        requestDTO.setShowId(showSeats.get(0).getShow().getId());
        BookingResponseDto responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.FAILURE, responseDTO.getResponseStatus(), "Status should be FAILURE");
        assertNull(responseDTO.getTicket(), "Ticket should be null");
    }

    @Test
    public void testBookTicket_InvalidShowSeat_Failure() throws UserNotFoundException, ShowNotFoundException {
        BookingRequestDto requestDTO = new BookingRequestDto();
        requestDTO.setShowSeatIds(List.of(showSeats.get(0).getId(), 100));
        requestDTO.setUserId(user.getId());
        requestDTO.setShowId(showSeats.get(0).getShow().getId());
        BookingResponseDto responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.FAILURE, responseDTO.getResponseStatus(), "Status should be FAILURE");
        assertNull(responseDTO.getTicket(), "Ticket should be null");
    }


    @Test
    public void testBookTicket_BookABookedTicket_Failure() throws Exception {
        BookingRequestDto requestDTO = new BookingRequestDto();
        requestDTO.setShowSeatIds(List.of(showSeats.get(0).getId(), showSeats.get(1).getId()));
        requestDTO.setShowId(showSeats.get(0).getShow().getId());
        requestDTO.setUserId(user.getId());
        BookingResponseDto responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, responseDTO.getResponseStatus(), "Status should be SUCCESS");
        assertNotNull(responseDTO.getTicket(), "Ticket should not be null");
        assertNotNull(responseDTO.getTicket().getSeats(), "Seats should not be null");
        assertEquals(2, responseDTO.getTicket().getSeats().size(), "Seats should be 2");

        Ticket ticket = responseDTO.getTicket();
        int ticketId = ticket.getId();
        ticketRepository.findById(ticketId).orElseThrow(() -> new Exception("Ticket should be stored in database"));

        requestDTO = new BookingRequestDto();
        requestDTO.setShowSeatIds(List.of(showSeats.get(0).getId(), showSeats.get(1).getId()));
        requestDTO.setShowId(showSeats.get(0).getShow().getId());
        requestDTO.setUserId(user.getId());
        responseDTO = ticketController.bookTicket(requestDTO);
        assertEquals(ResponseStatus.FAILURE, responseDTO.getResponseStatus(), "Status should be FAILURE");
        assertNull(responseDTO.getTicket(), "Ticket should be null");
    }
}

