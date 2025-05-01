package com.shann.bookmyshow.service;

import com.shann.bookmyshow.entity.Booking;
import com.shann.bookmyshow.exceptions.ShowNotFoundException;
import com.shann.bookmyshow.exceptions.UserNotFoundException;

import java.util.List;

public interface BookingService {

    public Booking bookTicket(Long userId, Long showId, List<Integer> showSeatIds) throws UserNotFoundException, ShowNotFoundException;
}
