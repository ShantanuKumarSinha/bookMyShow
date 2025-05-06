package com.shann.bookmyshow.dtos;

import lombok.Data;

@Data
public class RateMovieRequestDto {
    private int userId;
    private int movieId;
    private int rating;
}
