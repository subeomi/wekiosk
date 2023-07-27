package com.project.wekiosk.payment.search;

import com.project.wekiosk.payment.Payment;
import com.project.wekiosk.payment.dto.PageRequestDTO;
import com.project.wekiosk.payment.dto.PageResponseDTO;
import com.project.wekiosk.payment.dto.PaymentListDTO;

import java.time.LocalDate;
import java.util.List;

public interface PaymentSearch {

    PageResponseDTO<PaymentListDTO> list(PageRequestDTO pageRequestDTO);

}
