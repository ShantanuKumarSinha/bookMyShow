package com.shann.bookmyshow.dto;

import lombok.Data;
import java.util.List;

@Data
public class BookingRequestDTO {
   private Long userId;
   private Long showId;
   private List<Integer> showSeatIds;

}
