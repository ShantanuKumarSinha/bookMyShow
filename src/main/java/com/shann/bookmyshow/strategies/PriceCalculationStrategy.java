package com.shann.bookmyshow.strategies;

import com.shann.bookmyshow.entities.ShowSeat;

import java.util.List;

public interface PriceCalculationStrategy {
    double calculatePrice(List<ShowSeat> showSeats, double taxRate);
}
