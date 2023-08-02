package com.project.wekiosk.payment.service;

import com.project.wekiosk.payment.domain.Payment;
import com.project.wekiosk.payment.dto.*;
import com.project.wekiosk.payment.repository.PaymentRepository;
import com.project.wekiosk.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService {

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

        List<ProductDTO> product = new ArrayList<>();
        payment.getOrders().getDetails().forEach(d ->
            product.add(ProductDTO.builder()
                    .pno(d.getProduct().getPno())
                    .pname(d.getProduct().getPname())
                    .pprice(d.getProduct().getPprice())
                    .build()));


        return PaymentDTO.builder()
                .payno(payment.getPayno())
                .total_price(payment.getTotal_price())
                .pay_method(payment.getPay_method())
                .pay_status(payment.getPay_status())
                .pay_date(payment.getPay_date())
                .ostatus(payment.getOrders().getOstatus())
                .products(product)
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
    public List<Long> getSales(int year, int month) {

        return repository.dailySalesByMonth(year, month);
    }

    @Override
    public Long getLastMonthSales(int year, int month) {

        return repository.lastMonthSales(year, month);
    }

}
