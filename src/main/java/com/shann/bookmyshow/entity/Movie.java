package com.shann.bookmyshow.entity;

import com.shann.bookmyshow.enums.Genre;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Movie extends BaseModel {
    private String title;
    @Enumerated(EnumType.ORDINAL)
    private Genre genre;
    private Long duration;
    @ManyToMany
    private List<Cast> casts;
}
