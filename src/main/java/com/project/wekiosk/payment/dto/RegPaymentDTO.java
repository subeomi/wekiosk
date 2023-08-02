package com.project.wekiosk.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegPaymentDTO {

    private Long payno;

    private Long total_price;

    private String pay_method;

    private String pay_status;

    private LocalDateTime pay_date;

    private int ono;

}
