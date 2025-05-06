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
    private Long duration;
    @ManyToMany
    private List<Cast> casts;
}
