package com.shann.bookmyshow.repositories;

import com.shann.bookmyshow.entities.Movie;
import com.shann.bookmyshow.entities.Rating;
import com.shann.bookmyshow.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @EntityGraph(attributePaths = "movie")
    List<Rating> findByMovie_Id(int movieId);

    Optional<Rating> findByMovieAndUser(Movie movie, User user);
}
