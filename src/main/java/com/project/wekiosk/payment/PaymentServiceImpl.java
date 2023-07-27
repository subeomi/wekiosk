package com.project.wekiosk.payment;

import com.project.wekiosk.order.OrdersRepository;
import com.project.wekiosk.payment.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository repository;

    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<PaymentListDTO> list(PageRequestDTO requestDTO) {

        return repository.list(requestDTO);
    }

    @Override
    public PaymentDTO getOne(Long payno) {

        Optional<Payment> result = repository.findById(payno);

        Payment payment = result.orElseThrow();

        return PaymentDTO.builder()
                .payno(payment.getPayno())
                .total_price(payment.getTotal_price())
                .pay_method(payment.getPay_method())
                .pay_status(payment.getPay_status())
                .pay_date(payment.getPay_date())
                .ostatus(payment.getOrders().getOstatus())
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
    public List<Long> getSales(int year,int month) {

        return repository.dailySalesByMonth(year, month);
    }

}