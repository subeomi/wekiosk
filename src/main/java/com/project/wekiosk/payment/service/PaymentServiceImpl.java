package com.project.wekiosk.payment.service;

import com.project.wekiosk.order.repository.OrdersRepository;
import com.project.wekiosk.payment.domain.Payment;
import com.project.wekiosk.payment.dto.*;
import com.project.wekiosk.payment.repository.PaymentRepository;
import com.project.wekiosk.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<PaymentListDTO> list(Long sno, PageRequestDTO requestDTO) {

        return repository.list(sno, requestDTO);
    }

    @Override
    public PaymentDTO getOne(Long payno) {

        Optional<Payment> result = repository.findById(payno);

        Payment payment = result.orElseThrow();

        List<ProductDTO> product = new ArrayList<>();
        payment.getOrders().getDetails().forEach(d ->
                product.add(ProductDTO.builder()
                        .pno(d.getProduct().getPno())
                        .pname(d.getProduct().getPname())
                        .pprice(d.getProduct().getPprice())
                        .quantity(d.getQuantity())
                        .build()));

        return PaymentDTO.builder()
                .payno(payment.getPayno())
                .total_price(payment.getTotal_price())
                .pay_method(payment.getPay_method())
                .pay_status(payment.getPay_status())
                .pay_date(payment.getPay_date())
                .ostatus(payment.getOrders().getOstatus())
                .products(product)
                .ono(payment.getOrders().getOno())
                .build();
    }

    @Override
    public Long register(RegPaymentDTO regPaymentDTO) {

        Payment payment = modelMapper.map(regPaymentDTO, Payment.class);

        return repository.save(payment).getPayno();
    }

    @Override
    public void modify(PaymentDTO paymentDTO) {

        Optional<Payment> result = repository.findById(paymentDTO.getPayno());

        Payment payment = result.orElseThrow();

        payment.changePayStatus(paymentDTO.getPay_status());

        repository.save(payment);
    }

    @Override
    public Map<String, Long> getSales(Long sno, LocalDate date) {

        Map<String, Long> salesMap = new HashMap<>();

        // 해당 일 판매 건수
//        Long saleCount = repository.saleCount(sno, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
//        salesMap.put("saleCount", saleCount);

        // 해당 일 결제 상태 건수 // [결제완료, 환불]
        repository.pStatusCount(sno, date.getYear(), date.getMonthValue(), date.getDayOfMonth())
                .forEach(result -> {
                    String status = (String) result[0];
                    Long count = (Long) result[1];
                    salesMap.put(status, count);
                });

        // 해당 일 주문 상태 건수 // [준비중, 완료]
        repository.oStatusCount(sno, date.getYear(), date.getMonthValue(), date.getDayOfMonth())
                .forEach(result -> {
                    String status = (int) result[0] == 0 ? "preparing" : "prepared";
                    Long count = (Long) result[1];
                    salesMap.put(status, count);
                });

        // 해당 월 매출 // 날짜: 매출
        repository.dailySalesByMonth(sno, date.getYear(), date.getMonthValue())
                .forEach(result -> {
                    Date rDate = (Date) result[0];
                    Long sale = (Long) result[1];
                    salesMap.put(rDate.toString(), sale);
                });

        // 전달 월 매출 합계
        date = date.minusMonths(1);
        Long lastSale = repository.lastMonthSale(sno, date.getYear(), date.getMonthValue());
        salesMap.put(date.toString().substring(0, 7), lastSale == null ? 0L : lastSale);

        return salesMap;
    }

}