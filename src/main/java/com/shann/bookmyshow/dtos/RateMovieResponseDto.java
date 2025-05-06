package com.shann.bookmyshow.dtos;


import com.shann.bookmyshow.entities.Rating;
import lombok.Data;

@Data
public class RateMovieResponseDto {
    private ResponseStatus responseStatus;
    private Rating rating;
}
