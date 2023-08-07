package com.project.wekiosk.option.repository;

import com.project.wekiosk.option.domain.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OptionsRepository extends JpaRepository<Options, Long> {

//    Optional<Options> findByPnoAndOrd(Long pno, Long ord);


    Optional<Options> findById(Long ord);


    @Query("SELECT MAX(o.ord) FROM Options o WHERE o.product.pno = ?1")
    Optional<Long> findMaxOrdByPno(Long pno);
}
