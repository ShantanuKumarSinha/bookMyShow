package com.shann.bookmyshow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
