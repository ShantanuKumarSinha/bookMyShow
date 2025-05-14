package com.shann.bookmyshow.repositories;

import com.shann.bookmyshow.entities.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Integer> {
}
