package com.shann.bookmyshow.services.impl;

import com.shann.bookmyshow.entities.Screen;
import com.shann.bookmyshow.entities.SeatTypeShow;
import com.shann.bookmyshow.entities.Show;
import com.shann.bookmyshow.entities.ShowSeat;
import com.shann.bookmyshow.enums.*;
import com.shann.bookmyshow.exceptions.*;
import com.shann.bookmyshow.repositories.*;
import com.shann.bookmyshow.services.ShowService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ShowServiceImpl is the implementation of the ShowService interface.
 * It provides the functionality to create a show.
 */
@Service
public class ShowServiceImpl implements ShowService {

    /**
     * Repositories for accessing the database.
     */

    private MovieRepository movieRepository;
    private UserRepository userRepository;
    private ScreenRepository screenRepository;
    private ShowRepository showRepository;
    private SeatRepository seatRepository;
    private ShowSeatRepository showSeatRepository;
    private SeatTypeShowRepository seatTypeShowRepository;

    /**
     * Constructor for ShowServiceImpl.
     *
     * @param movieRepository        the movie repository
     * @param userRepository         the user repository
     * @param screenRepository       the screen repository
     * @param showRepository         the show repository
     * @param seatRepository         the seat repository
     * @param showSeatRepository     the show seat repository
     * @param seatTypeShowRepository the seat type show repository
     */

    public ShowServiceImpl(MovieRepository movieRepository, UserRepository userRepository, ScreenRepository screenRepository, ShowRepository showRepository, SeatRepository seatRepository,
                           ShowSeatRepository showSeatRepository, SeatTypeShowRepository seatTypeShowRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.screenRepository = screenRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.showSeatRepository = showSeatRepository;
        this.seatTypeShowRepository = seatTypeShowRepository;
    }

    /**
     * This method creates a show.
     *
     * @param userId        the id of the user creating the show
     * @param movieId       the id of the movie
     * @param screenId      the id of the screen
     * @param startTime     the start time of the show
     * @param endTime       the end time of the show
     * @param pricingConfig the pricing configuration for the show
     * @param features      the features of the show
     * @return the created show
     */
    @Override
    public Show createShow(int userId, int movieId, int screenId, Date startTime, Date endTime, List<Pair<SeatType, Double>> pricingConfig, List<Feature> features) throws MovieNotFoundException, ScreenNotFoundException, FeatureNotSupportedByScreen, InvalidDateException, UserNotFoundException, UnAuthorizedAccessException {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        if (!user.getUserType().equals(UserType.ADMIN))
            throw new UnAuthorizedAccessException();
        var movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        var screen = screenRepository.findById(screenId).orElseThrow(() -> new ScreenNotFoundException());
        if (startTime.after(endTime))
            throw new InvalidDateException("Start time should be before end time");
        if (startTime.before(new Date()))
            throw new InvalidDateException("Start time should be after current time");
        // check if the screens have features required for the show
        featuresCheck(features, screen);
        // create the show
        var show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(startTime);
        show.setEndTime(endTime);
        show.setFeatures(features);
        show.setStatus(Status.AVAILABLE);
        show.setShowDate(new Date());
        show = showRepository.save(show);

        var finalShow = show;

        // create the show seats
        var showSeats = new ArrayList<ShowSeat>();

        var seats = seatRepository.findAllByScreenId(screenId);
        seats.forEach(seat -> {
            var showSeat = new ShowSeat();
            showSeat.setSeat(seat);
            showSeat.setShow(finalShow);
            showSeat.setShowSeatStaus(ShowSeatStatus.AVAILABLE);
            showSeats.add(showSeat);
        });
        showSeatRepository.saveAll(showSeats);
        // create the seat type show
        pricingConfig.forEach(pair -> {
            var seatTypeShow = new SeatTypeShow();
            seatTypeShow.setSeatType(pair.getFirst());
            seatTypeShow.setPrice(pair.getSecond());
            seatTypeShow.setShow(finalShow);
            seatTypeShow = seatTypeShowRepository.save(seatTypeShow);
        });
        return show;
    }

    private void featuresCheck(List<Feature> features, Screen screen) throws FeatureNotSupportedByScreen {
        for (Feature feature : features) {
            if (!screen.getFeatures().contains(feature))
                throw new FeatureNotSupportedByScreen();
        }
        ;
    }

}
