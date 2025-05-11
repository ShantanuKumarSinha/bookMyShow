package com.shann.bookmyshow.repositories;

import com.shann.bookmyshow.entities.Cast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CastRepository extends JpaRepository<Cast, Integer> {
}
