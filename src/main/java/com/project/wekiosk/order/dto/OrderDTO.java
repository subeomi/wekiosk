package com.project.wekiosk.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long ono;

    @Builder.Default
    private int ostatus = 0;

    private Long sno;

    private List<OrderDetailDTO> details;

}
