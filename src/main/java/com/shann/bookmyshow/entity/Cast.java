package com.shann.bookmyshow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Cast extends BaseModel {
    private String castName;
    private Date dateOfBirth;
}
