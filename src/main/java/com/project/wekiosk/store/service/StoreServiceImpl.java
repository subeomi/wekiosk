package com.project.wekiosk.store.service;

import com.project.wekiosk.store.domain.Store;
import com.project.wekiosk.store.dto.StoreDTO;
import com.project.wekiosk.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{

    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;


    @Override
    public String register(StoreDTO storeDTO) {

        Store store = modelMapper.map(storeDTO, Store.class);

        storeRepository.save(store);

        return storeDTO.getSname();
    }

    @Override
    public StoreDTO getOne(Long sno) {

        Optional<Store> result = storeRepository.findById(sno);

        Store store = result.orElseThrow();

        StoreDTO dto = modelMapper.map(store, StoreDTO.class);

        return dto;
    }

    @Override
    public void modifier(StoreDTO storeDTO) {

        Optional<Store> result = storeRepository.findById(storeDTO.getSno());

        Store store = result.orElseThrow();

        store.changeSaddress(storeDTO.getSaddress());
        store.changeSname(storeDTO.getSname());
        store.changeScontact(storeDTO.getScontact());

        storeRepository.save(store);
    }

    @Override
    public void delete(Long sno) {

        Optional<Store> result = storeRepository.findById(sno);

        Store store = result.orElseThrow();

        store.delAccount();

        storeRepository.save(store);
    }
}
