package com.shann.bookmyshow.controllers;

import com.shann.bookmyshow.dtos.*;
import com.shann.bookmyshow.dtos.ResponseStatus;
import com.shann.bookmyshow.services.RatingsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingsController {

    private RatingsService ratingsService;

    public RatingsController(RatingsService ratingsService) {
        this.ratingsService = ratingsService;
    }

    @PostMapping("")
    public RateMovieResponseDto rateMovie(@RequestBody RateMovieRequestDto requestDto) {
        var responseDto = new RateMovieResponseDto();
        try {
            var rating = ratingsService.rateMovie(requestDto.getUserId(), requestDto.getMovieId(), requestDto.getRating());
            responseDto.setRating(rating);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception exception) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }

    @GetMapping("")
    public GetAverageMovieResponseDto getAverageMovieRating(@RequestBody GetAverageMovieRequestDto requestDto) {
        var responseDto = new GetAverageMovieResponseDto();
        try {
            var averageRating = ratingsService.getAverageRating(requestDto.getMovieId());
            responseDto.setAverageRating(averageRating);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception exception) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
