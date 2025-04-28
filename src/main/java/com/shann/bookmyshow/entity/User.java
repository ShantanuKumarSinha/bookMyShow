package com.shann.bookmyshow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User extends BaseModel {
    private String username;
    private String email;
    private String password;
}
