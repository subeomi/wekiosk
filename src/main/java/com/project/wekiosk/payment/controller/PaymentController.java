package com.project.wekiosk.payment.controller;

import com.project.wekiosk.payment.service.PaymentService;
import com.project.wekiosk.payment.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService service;

    @GetMapping("/list")
    public PageResponseDTO<PaymentListDTO> list(PageRequestDTO requestDTO) {

        return service.list(requestDTO);
    }

    @GetMapping("/{payno}")
    public PaymentDTO getOne(@PathVariable("payno") Long payno) {

        return service.getOne(payno);
    }

    @PostMapping("")
    public Long register(@RequestBody RegPaymentDTO regPaymentDTO) {

        return service.register(regPaymentDTO);
    }

    @PutMapping("")
    public Map<String, String> modify(@RequestBody PaymentDTO paymentDTO){

        service.modify(paymentDTO);

        return Map.of("result", "success");
    }

    @GetMapping("/sales")
    public List<Long> getSales(@RequestParam("year") int year, @RequestParam("month") int month){

        Long lastMonth = service.getLastMonthSales(year, month-1);
        List<Long> Sales = service.getSales(year, month);
        Sales.add(0, lastMonth);
        return Sales;
    }

}
