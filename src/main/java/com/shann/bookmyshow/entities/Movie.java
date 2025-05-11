package com.shann.bookmyshow.entities;

import com.shann.bookmyshow.enums.Genre;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Movie extends BaseModel {
    private String name;
    @Enumerated(EnumType.ORDINAL)
    private Genre genre;
    private String description;
    private Long duration;
    @ManyToMany
    @JoinTable(name = "movie_cast",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "cast_id"))
    private List<Cast> casts;
}
