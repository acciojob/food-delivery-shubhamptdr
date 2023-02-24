package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.io.repository.UserRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public OrderDto createOrder(OrderDto order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCost(order.getCost());
        orderEntity.setOrderId(order.getOrderId());
        orderEntity.setItems(order.getItems());
        orderEntity.setUserId(UUID.randomUUID().toString());

        UserEntity userEntity = userRepository.findByUserId(order.getUserId());
        List<OrderEntity> orderEntityList = userEntity.getListOfOrders();
        orderEntityList.add(orderEntity);
        userEntity.setListOfOrders(orderEntityList);

        // child automatically save by cascade
        userRepository.save(userEntity);
        order.setId(orderRepository.findByOrderId(order.getOrderId()).getId());
        return order;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if(orderEntity == null){
            throw new Exception("Order not found");
        }
        OrderDto dto = new OrderDto();
        dto.setId(orderEntity.getId());
        dto.setOrderId(orderEntity.getOrderId());
        dto.setUserId(orderEntity.getUserId());
        dto.setCost(orderEntity.getCost());
        dto.setItems(orderEntity.getItems());
        dto.setStatus(orderEntity.isStatus());
        return dto;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if (orderEntity == null){
            throw new Exception("Order not found");
        }

        orderEntity.setCost(order.getCost());
        orderEntity.setItems(order.getItems());
        orderEntity.setUserId(order.getUserId());

        // -----
        orderRepository.save(orderEntity);

        order.setId(orderEntity.getId());
        order.setOrderId(orderEntity.getOrderId());
        order.setStatus(orderEntity.isStatus());
        return order;
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        long id = orderRepository.findByOrderId(orderId).getId();
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDto> getOrders() {

        List<OrderEntity> orderEntityList = (List<OrderEntity>) orderRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for(OrderEntity o : orderEntityList){
            OrderDto orderDto = new OrderDto();
            orderDto.setOrderId(o.getOrderId());
            orderDto.setId(o.getId());
            orderDto.setUserId(o.getUserId());
            orderDto.setStatus(o.isStatus());
            orderDto.setItems(o.getItems());
            orderDto.setCost(o.getCost());
            orderDtoList.add(orderDto);
        }
        return orderDtoList;

    }
}