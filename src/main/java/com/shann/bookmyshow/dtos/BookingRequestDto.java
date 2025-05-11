package com.shann.bookmyshow.dtos;

import lombok.Data;
import java.util.List;

@Data
public class BookingRequestDto {
   private Integer userId;
   private Integer showId;
   private List<Integer> showSeatIds;

}
