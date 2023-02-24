package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.*;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	OrderService orderService;
	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{
		OrderDetailsResponse returnValue = new OrderDetailsResponse();

		OrderDto orderDto = orderService.getOrderById(id);
		BeanUtils.copyProperties(orderDto, returnValue);

		return returnValue;

	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		OrderDetailsResponse returnValue = new OrderDetailsResponse();

		OrderDto dto = new OrderDto();
		BeanUtils.copyProperties(order,dto);

		OrderDto orderDto = orderService.createOrder(dto);
		BeanUtils.copyProperties(orderDto, returnValue);

		return returnValue;
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		OrderDetailsResponse returnValue = new OrderDetailsResponse();

		OrderDto dto = new OrderDto();
		BeanUtils.copyProperties(order,dto);

		OrderDto orderDto = orderService.updateOrderDetails(id,dto);
		BeanUtils.copyProperties(orderDto, returnValue);

		return returnValue;
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName(RequestOperationName.DELETE.name());

		try{
			orderService.deleteOrder(id);
		}catch (Exception e){
			operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
			return operationStatusModel;
		}
		operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		List<OrderDto> list = orderService.getOrders();
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
