package com.shann.bookmyshow.controllers;

import com.shann.bookmyshow.dtos.CreateShowRequestDto;
import com.shann.bookmyshow.dtos.CreateShowResponseDto;
import com.shann.bookmyshow.dtos.ResponseStatus;
import com.shann.bookmyshow.services.ShowService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;

    }

    public CreateShowResponseDto createShow(CreateShowRequestDto requestDTO) {
        var responseDTO = new CreateShowResponseDto();
        try {
            var show = showService.createShow(requestDTO.getUserId(),
                    requestDTO.getMovieId(),
                    requestDTO.getScreenId(),
                    requestDTO.getStartTime(),
                    requestDTO.getEndTime(),
                    requestDTO.getPricingConfig(),
                    requestDTO.getFeatures());
            responseDTO.setShow(show);
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }
}
