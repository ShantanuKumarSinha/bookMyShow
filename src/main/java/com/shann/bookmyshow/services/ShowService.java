package com.shann.bookmyshow.services;

import com.shann.bookmyshow.entities.Show;
import com.shann.bookmyshow.enums.Feature;
import com.shann.bookmyshow.enums.SeatType;
import com.shann.bookmyshow.exceptions.*;
import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;

public interface ShowService {
    public Show createShow(int userId, int movieId, int screenId, Date startTime, Date endTime, List<Pair<SeatType, Double>> pricingConfig, List<Feature> features) throws MovieNotFoundException, ScreenNotFoundException, FeatureNotSupportedByScreen, InvalidDateException, UserNotFoundException, UnAuthorizedAccessException;
}
