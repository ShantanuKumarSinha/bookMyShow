package com.shann.bookmyshow.dtos;

import lombok.Data;
import java.util.List;

@Data
public class BookingRequestDTO {
   private Long userId;
   private Long showId;
   private List<Integer> showSeatIds;

}
