package com.project.wekiosk.order.service;

import com.project.wekiosk.order.domain.Orders;
import com.project.wekiosk.order.dto.OrderDTO;
import com.project.wekiosk.order.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService{

    private final OrdersRepository repository;

    private final ModelMapper modelMapper;

    @Override
    public void modifyOs(OrderDTO orderDTO) {

        Optional<Orders> result = repository.findById(orderDTO.getOno());

        Orders orders = result.orElseThrow();

        orders.changeOstatus(orderDTO.getOstatus());

        repository.save(orders);

    }

    @Override
    public Long register(OrderDTO orderDTO) {

        Orders orders = modelMapper.map(orderDTO, Orders.class);

        orderDTO.getQuantity().forEach(q -> orders.addOrderDetail(q));

        return repository.save(orders).getOno();

    }
}
