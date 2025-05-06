package com.shann.bookmyshow.entities;

import com.shann.bookmyshow.enums.PaymentMode;
import com.shann.bookmyshow.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Entity
@Data
public class Payment extends BaseModel {
    private Double amount;
    @Enumerated(EnumType.ORDINAL)
    private PaymentMode paymentMode;
    private String referenceNumber;
    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;
}
