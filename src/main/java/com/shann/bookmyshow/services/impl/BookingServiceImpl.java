package com.shann.bookmyshow.services.impl;

import com.shann.bookmyshow.entities.Booking;
import com.shann.bookmyshow.entities.Payment;
import com.shann.bookmyshow.enums.BookingStatus;
import com.shann.bookmyshow.enums.PaymentMode;
import com.shann.bookmyshow.enums.PaymentStatus;
import com.shann.bookmyshow.enums.ShowSeatStatus;
import com.shann.bookmyshow.exceptions.ShowNotFoundException;
import com.shann.bookmyshow.exceptions.ShowSeatsNotValidException;
import com.shann.bookmyshow.exceptions.UserNotFoundException;
import com.shann.bookmyshow.repositories.BookingRepository;
import com.shann.bookmyshow.repositories.ShowRepository;
import com.shann.bookmyshow.repositories.ShowSeatRepository;
import com.shann.bookmyshow.repositories.UserRepository;
import com.shann.bookmyshow.services.BookingService;
import com.shann.bookmyshow.strategies.PriceCalculationStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class BookingServiceImpl implements BookingService {
    /**
     * Repositories for user, show, show seat and booking.
     * These are used to fetch the user, show and show seat details from the database.
     * Repositories are injected using constructor injection.
     * PriceCalculationStrategy is used to calculate the price of the ticket.
     */

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private BookingRepository bookingRepository;
    private PriceCalculationStrategy priceCalculationStrategy;
    // Using ReentrantLock to lock the show seats
    //private Lock lock = new ReentrantLock();

    @Value("${tax.rate}")
    private double taxRate;

    /**
     * Constructor for BookingServiceImpl.
     * @param userRepository
     * @param showRepository
     * @param showSeatRepository
     * @param bookingRepository
     * @param priceCalculationStrategy
     */

    public BookingServiceImpl(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, BookingRepository bookingRepository,PriceCalculationStrategy priceCalculationStrategy) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.priceCalculationStrategy = priceCalculationStrategy;
    }

    /**
     * This method books a ticket for a user.
     * @param userId
     * @param showId
     * @param showSeatIds
     * @return
     * @throws UserNotFoundException
     * @throws ShowNotFoundException
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookTicket(Long userId, Long showId, List<Integer> showSeatIds) throws UserNotFoundException, ShowNotFoundException {
        // Logic to book a ticket
        var booking = new Booking();
        // Set booking details. Fetch user and show details by their IDs
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        var show = showRepository.findById(showId).orElseThrow(ShowNotFoundException::new);
        // Fetch show seats by their IDs
        // Using ReentrantLock would have worked only in single server instance and not in distributed environment
        // lock.lock();
        //var showSeats = showSeatRepository.findAllById(showSeatIds);
        // If we lock the show seats in showSeatRepository, it is safe for distributed environment
        var showSeats = showSeatRepository.findAllByIdAndLock(showSeatIds);
        // All the show seats should be valid otherwise throw ShowSeatNotValidException
        if (showSeats.size() != showSeatIds.size()) {
            throw new ShowSeatsNotValidException();
        }

        // Seat Availability check
        showSeats.forEach(showSeat -> {
            if (!showSeat.getShowSeatStaus().equals(ShowSeatStatus.AVAILABLE)) {
                throw new ShowSeatsNotValidException();
            }
        });
        // start booking
        booking.setUser(user);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setBookingNumber("Booking" + user.getId() + "-" + show.getId());

        // Block the seats first
        showSeats.forEach(showSeat -> showSeat.setShowSeatStaus(ShowSeatStatus.BLOCKED));
        showSeatRepository.saveAll(showSeats);
        // Add the show seats to the booking
        booking.setSeats(showSeats);
        // After blocking the seats, we can proceed with the payment
        // Calculate the price of the ticket
        var ticketPrice = priceCalculationStrategy.calculatePrice(showSeats, taxRate);
        booking.setAmount(ticketPrice);

        // Set the ticket price in the payment object
        // payment is not important here
        var payment = new Payment();
        payment.setAmount(ticketPrice);
        payment.setPaymentMode(PaymentMode.UPI);
        payment.setReferenceNumber(booking.getBookingNumber() + "-" + booking.getLastModifiedAt().getTime());
        // assume you called the payment gateway
        // paymentGateway.processPayment(payment);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);

        // Set the show seats to occupied
        showSeats.forEach(showSeat -> showSeat.setShowSeatStaus(ShowSeatStatus.OCCUPIED));
        booking.setPayments(List.of(payment));
        booking.setBookingStatus(BookingStatus.BOOKED);
        // Save the booking to the database
        // If payment fails or after timeout of 10 min release the seats
        return bookingRepository.save(booking);
    }
}
