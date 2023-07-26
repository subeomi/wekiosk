package com.project.wekiosk.faq.repository;

import com.project.wekiosk.domain.Faq;
import com.project.wekiosk.faq.repository.search.FaqSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long>, FaqSearch {

}
