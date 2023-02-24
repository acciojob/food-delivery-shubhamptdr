package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.io.repository.UserRepository;
import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        orderEntity.setUserId(order.getUserId());
        orderEntity.setItems(order.getItems());
        orderEntity.setStatus(order.isStatus());

        orderRepository.save(orderEntity);

//        UserEntity userEntity = userRepository.findByUserId(order.getUserId());
//        List<OrderEntity> orderEntityList = userEntity.getListOfOrders();
//        orderEntityList.add(orderEntity);
//        userEntity.setListOfOrders(orderEntityList);

        // child automatically save by cascade
//        userRepository.save(userEntity);
        order.setId(orderRepository.findByOrderId(order.getOrderId()).getId());
        return order;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
//        if(orderEntity == null){
//            throw new Exception("Order not found");
//        }
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
//        if (orderEntity == null){
//            throw new Exception("Order not found");
//        }

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


    /*----------------------------------------------------------------*/

    public OrderDetailsResponse get_Order(String id) throws Exception {
        OrderDetailsResponse returnValue = new OrderDetailsResponse();

        OrderDto orderDto = getOrderById(id);

        returnValue.setOrderId(orderDto.getOrderId());
        returnValue.setCost(orderDto.getCost());
        returnValue.setItems(orderDto.getItems());
        returnValue.setUserId(orderDto.getUserId());
        returnValue.setStatus(orderDto.isStatus());

        return returnValue;

    }

    public OrderDetailsResponse create_Order(OrderDetailsRequestModel order) {
        OrderDetailsResponse returnValue = new OrderDetailsResponse();

        OrderDto dto = new OrderDto();
        dto.setItems(order.getItems());
        dto.setCost(order.getCost());
        dto.setUserId(order.getUserId());

        OrderDto orderDto = createOrder(dto);

        returnValue.setOrderId(orderDto.getOrderId());
        returnValue.setCost(orderDto.getCost());
        returnValue.setItems(orderDto.getItems());
        returnValue.setUserId(orderDto.getUserId());
        returnValue.setStatus(orderDto.isStatus());

        return returnValue;

    }

    public OrderDetailsResponse update_Order(String id, OrderDetailsRequestModel order) throws Exception {
        OrderDetailsResponse returnValue = new OrderDetailsResponse();

        OrderDto dto = new OrderDto();
        dto.setUserId(order.getUserId());
        dto.setCost(order.getCost());
        dto.setItems(order.getItems());


        OrderDto orderDto = updateOrderDetails(id,dto);

        returnValue.setOrderId(orderDto.getOrderId());
        returnValue.setUserId(orderDto.getUserId());
        returnValue.setStatus(orderDto.isStatus());
        returnValue.setItems(orderDto.getItems());
        returnValue.setCost(orderDto.getCost());

        return returnValue;

    }

    public OperationStatusModel delete_Order(String id) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.toString());

        try{
            deleteOrder(id);
        }catch (Exception e){
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.toString());
            return operationStatusModel;
        }
        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.toString());
        return operationStatusModel;
    }

    public List<OrderDetailsResponse> get_Orders() {
        List<OrderDto> list = getOrders();
        List<OrderDetailsResponse> orderDetailsResponseList = new ArrayList<>();
        for (OrderDto o : list){
            OrderDetailsResponse dto = new OrderDetailsResponse();
            dto.setOrderId(o.getOrderId());
            dto.setCost(o.getCost());
            dto.setItems(o.getItems());
            dto.setUserId(o.getUserId());
            dto.setStatus(o.isStatus());


            orderDetailsResponseList.add(dto);
        }

        return orderDetailsResponseList;
    }
}