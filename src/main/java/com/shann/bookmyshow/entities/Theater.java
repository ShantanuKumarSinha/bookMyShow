package com.shann.bookmyshow.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Theater extends BaseModel {
    private String name;
    @OneToMany
    private List<Screen> screens;
}
