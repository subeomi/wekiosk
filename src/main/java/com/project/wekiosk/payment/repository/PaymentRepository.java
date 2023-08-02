package com.project.wekiosk.payment.repository;

import com.project.wekiosk.payment.domain.Payment;

import com.project.wekiosk.payment.repository.search.PaymentSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentSearch {

    @Query("select sum(p.total_price) from Payment p where YEAR(p.pay_date) = :year and MONTH(p.pay_date) = :month group by DATE(p.pay_date) order by DATE(p.pay_date)")
    List<Long> dailySalesByMonth(@Param("year") int year,@Param("month") int month);

    @Query("select sum(p.total_price) from Payment p where YEAR(p.pay_date) = :year and MONTH(p.pay_date) = :month order by DATE(p.pay_date)")
    Long lastMonthSales(@Param("year") int year,@Param("month") int month);

}
