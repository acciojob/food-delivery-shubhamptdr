package com.driver.service;

import java.util.List;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.shared.dto.OrderDto;

/**
 * Handle exception cases for all methods which throw Exception
 */
public interface OrderService {

	OrderDto createOrder(OrderDto order);
	OrderDto getOrderById(String orderId) throws Exception;
	OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception;
	void deleteOrder(String orderId) throws Exception;
	List<OrderDto> getOrders();

	OrderDetailsResponse get_Order(String id) throws Exception;

	OrderDetailsResponse create_Order(OrderDetailsRequestModel order);

	OrderDetailsResponse update_Order(String id, OrderDetailsRequestModel order) throws Exception;

	OperationStatusModel delete_Order(String id);

	List<OrderDetailsResponse> get_Orders();

}
