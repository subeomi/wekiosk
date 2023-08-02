package com.project.wekiosk.product.search;

import com.project.wekiosk.page.dto.PageRequestDTO;
import com.project.wekiosk.page.dto.PageResponseDTO;
import com.project.wekiosk.product.domain.Product1;
import com.project.wekiosk.product.domain.QProduct1;
import com.project.wekiosk.product.dto.ProductListDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(Product1.class);
    }

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {
        QProduct1 product = QProduct1.product1;

        JPQLQuery<Product1> query = from(QProduct1.product1); // 수정된 부분

        int pageNum = pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1;
        Pageable pageable = PageRequest.of(pageNum, pageRequestDTO.getSize(), Sort.by("pno").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<ProductListDTO> dtoQuery =
                query.select(
                        Projections.bean(ProductListDTO.class,
                                product.pno,
                                product.pname,
                                product.pprice,
                                product.pimage)
                );
        List<ProductListDTO> dtoList = dtoQuery.fetch();

        long totalCount = dtoQuery.fetchCount();

        return new PageResponseDTO<>(dtoList, totalCount, pageRequestDTO);
    }
}
