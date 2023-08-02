package com.project.wekiosk.payment.service;

import com.project.wekiosk.payment.dto.*;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface PaymentService {

    PageResponseDTO<PaymentListDTO> list(PageRequestDTO requestDTO);

    PaymentDTO getOne(Long payno);

    Long register(RegPaymentDTO regPaymentDTO);

    void modify(PaymentDTO paymentDTO);

    List<Long> getSales(int year, int month);

    Long getLastMonthSales(int year, int month);

}
