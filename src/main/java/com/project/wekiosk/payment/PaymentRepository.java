package com.project.wekiosk.payment;

import com.project.wekiosk.payment.Payment;
import com.project.wekiosk.payment.dto.PageResponseDTO;

import com.project.wekiosk.payment.dto.PaymentDTO;
import com.project.wekiosk.payment.search.PaymentSearch;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentSearch {

    @Query("select sum(p.total_price) from Payment p where YEAR(p.pay_date) = :year and MONTH(p.pay_date) = :month group by DATE(p.pay_date) order by DATE(p.pay_date)")
    List<Long> dailySalesByMonth(@Param("year") int year,@Param("month") int month);

}
