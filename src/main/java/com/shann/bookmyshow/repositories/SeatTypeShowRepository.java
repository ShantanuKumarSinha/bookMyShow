package com.shann.bookmyshow.repositories;

import com.shann.bookmyshow.entities.SeatTypeShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatTypeShowRepository extends JpaRepository<SeatTypeShow, Integer> {
}
