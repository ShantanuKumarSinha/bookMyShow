package com.shann.bookmyshow.entity;

import com.shann.bookmyshow.enums.Feature;
import com.shann.bookmyshow.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "movie_show")
public class Show extends BaseModel {
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Screen screen;
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> features;
    private LocalTime startTime;
    private LocalTime endTime;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
