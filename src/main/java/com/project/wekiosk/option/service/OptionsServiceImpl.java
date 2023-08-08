package com.project.wekiosk.option.service;

import com.project.wekiosk.option.domain.Options;
import com.project.wekiosk.option.dto.OptionsDTO;
import com.project.wekiosk.option.repository.OptionsRepository;
import com.project.wekiosk.product.domain.Product;
import com.project.wekiosk.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class OptionsServiceImpl implements OptionsService {
//
    private final OptionsRepository optionsRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OptionsServiceImpl(OptionsRepository optionsRepository, ProductRepository productRepository) {
        this.optionsRepository = optionsRepository;
        this.productRepository = productRepository;
    }
//
    @Override
    public List<OptionsDTO> getAllOptions(){
        List<Options> optionsList = optionsRepository.findAll();
        return optionsList.stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }
//    @Override
//    public OptionsDTO getOptionsByPnoAndOrd(Long pno, Long ord) {
//        Options options = optionsRepository.findByPnoAndOrd(pno, ord)
//                .orElseThrow(() -> new NoSuchElementException("옵션을 찾을 수 없습니다"));
//        return mapToDTO(options);
//    }
@Override
public void addOptions(String oname, Long oprice, Long pno) {
    // 옵션의 추가 순서를 데이터베이스에서 자동으로 관리하도록 설정
    long maxOrd = optionsRepository.findMaxOrdByPno(pno).orElse(0L);
    long newOrd = maxOrd + 1;

    // pno를 사용하여 product 엔티티를 가져옴
    Product product = productRepository.findById(pno)
            .orElseThrow(() -> new IllegalArgumentException("Invalid pno: " + pno));

    Options options = Options.builder()
            .oname(oname)
            .oprice(oprice)
            .product(product) // product 엔티티를 설정하여 연관 관계를 맺음
            .ord(newOrd)
            .build();
    optionsRepository.save(options);
}
//
//
//
//
//
//
//    @Override
//    public void saveOptions(OptionsDTO optionsDTO) {
//        Options options = mapToEntity(optionsDTO);
//        optionsRepository.save(options);
//
//    }
//
//
//    @Override
//    public void updateOptions(OptionsDTO optionsDTO) {
//        Options options = mapToEntity(optionsDTO);
//        optionsRepository.save(options);
//    }
//
//    @Override
//    public void deleteOptions(Long ord) {
//        optionsRepository.deleteById(ord);
//    }
//
    private OptionsDTO mapToDTO(Options options){
        return OptionsDTO.builder()
                .ord(options.getOrd())
                .oname(options.getOname())
                .oprice(options.getOprice())
                .pno(options.getProduct().getPno())
                .build();
    }

    private Options mapToEntity(OptionsDTO optionsDTO){
        return Options.builder()
                .ord(optionsDTO.getOrd())
                .oname(optionsDTO.getOname())
                .oprice(optionsDTO.getOprice())
                .build();
    }
}
