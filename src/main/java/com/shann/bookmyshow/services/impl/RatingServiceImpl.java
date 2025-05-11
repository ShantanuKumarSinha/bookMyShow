package com.shann.bookmyshow.services.impl;

import com.shann.bookmyshow.entities.Rating;
import com.shann.bookmyshow.exceptions.MovieNotFoundException;
import com.shann.bookmyshow.exceptions.UserNotFoundException;
import com.shann.bookmyshow.repositories.MovieRepository;
import com.shann.bookmyshow.repositories.RatingRepository;
import com.shann.bookmyshow.repositories.UserRepository;
import com.shann.bookmyshow.services.RatingsService;
import org.springframework.stereotype.Service;

/**
 * RatingServiceImpl is a service class that implements the RatingsService interface.
 * It provides methods to rate a movie and get the average rating of a movie.
 */
@Service
public class RatingServiceImpl implements RatingsService {
    /**
     * UserRepository is a repository interface for User entity.
     */
    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private RatingRepository ratingRepository;

    /**
     * Constructor for RatingServiceImpl.
     *
     * @param userRepository   UserRepository instance.
     * @param movieRepository  MovieRepository instance.
     * @param ratingRepository RatingRepository instance.
     */
    public RatingServiceImpl(UserRepository userRepository, MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    /**
     * This method rates a movie by a user.
     *
     * @param userId  The ID of the user.
     * @param movieId The ID of the movie.
     * @param rating  The rating given by the user.
     * @return The Rating object created.
     * @throws UserNotFoundException  If the user is not found.
     * @throws MovieNotFoundException If the movie is not found.
     */
    @Override
    public Rating rateMovie(int userId, int movieId, int rating) throws UserNotFoundException, MovieNotFoundException {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        var movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        var ratingObject = ratingRepository.findByMovieAndUser(movie, user).orElse(new Rating());
        ratingObject.setUser(user);
        ratingObject.setMovie(movie);
        ratingObject.setRating(rating);
        return ratingRepository.save(ratingObject);
    }

    @Override
    public double getAverageRating(int movieId) throws MovieNotFoundException {
        var ratings = ratingRepository.findByMovie_Id(movieId);
        return ratings.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));
    }
}
