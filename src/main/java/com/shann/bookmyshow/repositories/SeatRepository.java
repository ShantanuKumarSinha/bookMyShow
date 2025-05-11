package com.shann.bookmyshow.repositories;

import com.shann.bookmyshow.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findAllByScreenId(int screenId);
}
