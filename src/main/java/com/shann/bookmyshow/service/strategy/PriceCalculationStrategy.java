package com.shann.bookmyshow.service.strategy;

import com.shann.bookmyshow.entity.ShowSeat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PriceCalculationStrategy {

    /**
     * This method calculates the price of a ticket based on the base price and tax rate.
     *
     * @param showSeats The showSeat object.
     * @param taxRate   The tax rate to be applied.
     * @return The final price after applying the tax.
     */
    public double calculatePrice(List<ShowSeat> showSeats, double taxRate) {
        AtomicReference<Double> amount = new AtomicReference<>(0.0);
        showSeats.forEach(showSeat -> amount.updateAndGet(v -> v + showSeat.getPrice()));
        return amount.updateAndGet(v -> v + (v * taxRate));
    }

}
