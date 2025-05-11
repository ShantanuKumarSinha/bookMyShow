package com.shann.bookmyshow.dtos;


import com.shann.bookmyshow.entities.Show;
import lombok.Data;

@Data
public class CreateShowResponseDto {
    private ResponseStatus responseStatus;
    private Show show;
}
