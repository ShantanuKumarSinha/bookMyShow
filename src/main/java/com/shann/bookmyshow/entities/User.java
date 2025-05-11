package com.shann.bookmyshow.entities;

import com.shann.bookmyshow.enums.UserType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "bms_user")
public class User extends BaseModel {
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.ORDINAL)
    private UserType userType;
}
