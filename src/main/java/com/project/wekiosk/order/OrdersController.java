package com.project.wekiosk.order;

import com.project.wekiosk.order.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Log4j2
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping("")
    public Long register(@RequestBody OrderDTO orderDTO){
        return ordersService.register(orderDTO);
    }

    @PutMapping("")
    public Map<String, String> modify(@RequestBody OrderDTO orderDTO){

        log.info("c>>>>>>>>>>>>>>>>>>>>" + orderDTO);
        ordersService.modifyOs(orderDTO);

        return Map.of("result", "OK");
    }
}
