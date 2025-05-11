package com.shann.bookmyshow.services;

import com.shann.bookmyshow.entities.Ticket;
import com.shann.bookmyshow.exceptions.ShowNotFoundException;
import com.shann.bookmyshow.exceptions.UserNotFoundException;

import java.util.List;

public interface TicketService {

    public Ticket bookTicket(Integer userId, Integer showId, List<Integer> showSeatIds) throws UserNotFoundException, ShowNotFoundException;
}
