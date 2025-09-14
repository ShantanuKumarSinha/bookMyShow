package com.shann.bookmyshow.services.impl;

import com.shann.bookmyshow.entities.*;
import com.shann.bookmyshow.enums.TicketStatus;
import com.shann.bookmyshow.enums.PaymentMode;
import com.shann.bookmyshow.enums.PaymentStatus;
import com.shann.bookmyshow.enums.ShowSeatStatus;
import com.shann.bookmyshow.exceptions.ShowNotFoundException;
import com.shann.bookmyshow.exceptions.ShowSeatsNotValidException;
import com.shann.bookmyshow.exceptions.UserNotFoundException;
import com.shann.bookmyshow.repositories.*;
import com.shann.bookmyshow.services.TicketService;
import com.shann.bookmyshow.strategies.PriceCalculationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TicketServiceImpl is the implementation of the TicketService interface.
 * It provides methods to book tickets for users by blocking show seats.
 * It also calculates the ticket price using a specified price calculation strategy.
 */
@Service
public class TicketServiceImpl implements TicketService{
    
    private ShowSeatRepository showSeatRepository;

    private TicketRepository ticketRepository;

    private UserRepository userRepository;

    private PriceCalculationStrategy priceCalculationStrategy;

    @Value("${tax.rate}")
    private double taxRate;

    /**
     * Constructor for TicketServiceImpl.
     *
     * @param showSeatRepository Repository for ShowSeat entities.
     * @param ticketRepository Repository for Ticket entities.
     * @param userRepository Repository for User entities.
     * @param priceCalculationStrategy Strategy for calculating ticket prices.
     */
    @Autowired
    public TicketServiceImpl(ShowSeatRepository showSeatRepository, TicketRepository ticketRepository, UserRepository userRepository, PriceCalculationStrategy priceCalculationStrategy) {
        this.showSeatRepository = showSeatRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.priceCalculationStrategy = priceCalculationStrategy;
    }

    /**
     * This method books a ticket for a user by blocking the selected show seats.
     * It checks if the user and show seats are valid, and if the seats are available.
     * If the seats are available, it blocks them and creates a ticket with a pending status.
     *
     * @param showSeatIds List of show seat IDs to be booked
     * @param userId ID of the user booking the ticket
     * @return Ticket object with the booked seats and user information
     * @throws UserNotFoundException if the user does not exist
     * @throws ShowNotFoundException if the show seats are invalid or not available
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(List<Integer> showSeatIds, int userId) throws UserNotFoundException, ShowNotFoundException {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Invalid user"));

        List<ShowSeat> showSeats = showSeatRepository.findShowSeatsByIdIn(showSeatIds);
        if(showSeats.size() != showSeatIds.size()){
            throw new ShowNotFoundException("Invalid show seats");
        }

        Show show = showSeats.stream().map(ShowSeat::getShow).findFirst().orElseThrow(() -> new ShowSeatsNotValidException("Invalid show"));

        List<ShowSeat> bookedSeats = new ArrayList<>();
        for (ShowSeat showSeat : showSeats) {
            if(!showSeat.getShowSeatStaus().equals(ShowSeatStatus.AVAILABLE)){
                bookedSeats.add(showSeat);
            }
        }

        if(!bookedSeats.isEmpty()){
            throw new ShowSeatsNotValidException("Seats " + bookedSeats + " are not available");
        }

        showSeats.forEach(showSeat -> showSeat.setShowSeatStaus(ShowSeatStatus.BLOCKED));
        showSeatRepository.saveAll(showSeats);

        Ticket ticket = new Ticket();
        ticket.setBookingNumber(show.getId()+"-"+user.getId()+"-"+new Date().getTime());
        ticket.setUser(user);
        ticket.setSeats(showSeats);
        ticket.setTicketStatus(TicketStatus.PENDING);
        var amount = priceCalculationStrategy.calculatePrice(showSeats, taxRate);
        ticket.setAmount(amount);
        return ticketRepository.save(ticket);
    }
}
