package com.shann.bookmyshow.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "movie_user")
public class User extends BaseModel {
    private String username;
    private String email;
    private String password;
}
