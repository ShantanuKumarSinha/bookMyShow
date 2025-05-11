package com.shann.bookmyshow.entities;

import com.shann.bookmyshow.enums.Feature;
import com.shann.bookmyshow.enums.ScreenStatus;
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
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Feature> features;
    @Enumerated(EnumType.ORDINAL)
    @OneToMany(mappedBy = "screen")
    private List<Show> shows;
    @Enumerated(EnumType.ORDINAL)
    private ScreenStatus screenStatus;
}
