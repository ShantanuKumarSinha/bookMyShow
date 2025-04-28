package com.shann.bookmyshow.entity;

import com.shann.bookmyshow.enums.Feature;
import com.shann.bookmyshow.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "movie-show")
public class Show extends BaseModel {
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Screen screen;
    private List<Feature> features;
    private LocalTime startTime;
    private LocalTime endTime;
    private Status status;
}
