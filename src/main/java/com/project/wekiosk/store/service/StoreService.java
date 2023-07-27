package com.project.wekiosk.store.service;

import com.project.wekiosk.store.dto.StoreDTO;
import jakarta.transaction.Transactional;

@Transactional
public interface StoreService {

    String register(StoreDTO storeDTO);

    StoreDTO getOne(Long sno);

    void modifier(StoreDTO storeDTO);

    void delete(Long sno);
}
