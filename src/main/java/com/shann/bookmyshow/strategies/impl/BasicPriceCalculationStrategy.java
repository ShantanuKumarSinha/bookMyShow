package com.shann.bookmyshow.strategies.impl;

import com.shann.bookmyshow.entities.ShowSeat;
import com.shann.bookmyshow.strategies.PriceCalculationStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class BasicPriceCalculationStrategy implements PriceCalculationStrategy {

    /**
     * This method calculates the price of a ticket based on the base price and tax rate.
     *
     * @param showSeats The showSeat object.
     * @param taxRate   The tax rate to be applied.
     * @return The final price after applying the tax.
     */
    @Override
    public double calculatePrice(List<ShowSeat> showSeats, double taxRate) {
        AtomicReference<Double> amount = new AtomicReference<>(0.0);
        showSeats.forEach(showSeat -> amount.updateAndGet(v -> v + showSeat.getSeatTypeShow().getPrice()));
        return amount.updateAndGet(v -> v + (v * taxRate));
    }

}
