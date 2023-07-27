package com.project.wekiosk.faq;


import com.project.wekiosk.faq.search.FaqSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long>, FaqSearch {

}
