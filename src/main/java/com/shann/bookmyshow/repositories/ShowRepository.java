package com.shann.bookmyshow.repositories;

import com.shann.bookmyshow.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
}
