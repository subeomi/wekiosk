package com.project.wekiosk.payment.search;


import com.project.wekiosk.order.QOrders;
import com.project.wekiosk.payment.Payment;
import com.project.wekiosk.payment.QPayment;
import com.project.wekiosk.payment.dto.PageRequestDTO;
import com.project.wekiosk.payment.dto.PageResponseDTO;
import com.project.wekiosk.payment.dto.PaymentListDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.List;

public class PaymentSearchImpl extends QuerydslRepositorySupport implements PaymentSearch {

    public PaymentSearchImpl() {
        super(Payment.class);
    }

    @Override
    public PageResponseDTO<PaymentListDTO> list(PageRequestDTO pageRequestDTO) {

        QPayment payment = QPayment.payment;
        QOrders orders = QOrders.orders;

        JPQLQuery<Payment> query = from(payment);
        query.leftJoin(payment.orders, orders);

        int pageNum = pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1;

        Pageable pageable = PageRequest.of(pageNum, pageRequestDTO.getSize(), Sort.by("payno").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<PaymentListDTO> dtoQuery = query.select(Projections.bean(
                PaymentListDTO.class,
                payment.payno,
                payment.total_price,
                payment.pay_status,
                payment.pay_date));

        List<PaymentListDTO> dtoList = dtoQuery.fetch();

        long totalCount = dtoQuery.fetchCount();

        return new PageResponseDTO<>(dtoList,totalCount,pageRequestDTO);

    }

}
