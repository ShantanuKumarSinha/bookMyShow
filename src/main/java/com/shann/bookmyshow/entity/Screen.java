package com.shann.bookmyshow.entity;

import com.shann.bookmyshow.enums.Feature;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Screen extends BaseModel {
    private String title;
    @OneToMany(mappedBy = "screen")
    private List<Seat> seats;
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> features;
    @OneToMany(mappedBy = "screen")
    private List<Show> shows;
}
