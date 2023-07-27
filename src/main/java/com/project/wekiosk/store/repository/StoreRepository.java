package com.project.wekiosk.store.repository;

import com.project.wekiosk.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
