package com.shann.bookmyshow.entities;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Cast extends BaseModel {
    private String castName;
    private Date dateOfBirth;
}
