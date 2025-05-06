package com.shann.bookmyshow.repositories;

import com.shann.bookmyshow.entities.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ss FROM ShowSeat ss WHERE ss.id IN :showSeatIds")
    public List<ShowSeat> findAllByIdAndLock(List<Integer> showSeatIds);
}
