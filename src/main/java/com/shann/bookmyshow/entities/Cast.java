package com.shann.bookmyshow.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "bms_cast")
public class Cast extends BaseModel {
    private String castName;
    private Date dateOfBirth;
    @ManyToMany(mappedBy = "casts")
    private List<Movie> movies;
}
