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
		return orderService.get_Order(id);

	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		return orderService.create_Order(order);
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		return orderService.update_Order(id,order);
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		return orderService.delete_Order(id);
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		return orderService.get_Orders();
	}
}
