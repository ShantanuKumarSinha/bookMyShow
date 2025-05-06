package com.shann.bookmyshow.services;

import com.shann.bookmyshow.entities.Rating;
import com.shann.bookmyshow.exceptions.MovieNotFoundException;
import com.shann.bookmyshow.exceptions.UserNotFoundException;

public interface RatingsService {

    public Rating rateMovie(int userId, int movieId, int rating) throws UserNotFoundException, MovieNotFoundException;

    public double getAverageRating(int movieId) throws MovieNotFoundException;
}
