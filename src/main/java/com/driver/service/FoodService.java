package com.driver.service;

import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.shared.dto.FoodDto;

/**
 * Handle exception cases for all methods which throw Exception
 */
public interface FoodService {

	FoodDto createFood(FoodDto food);
	FoodDto getFoodById(String foodId) throws Exception;
	FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception;
	void deleteFoodItem(String id) throws Exception;
	List<FoodDto> getFoods();

    FoodDetailsResponse get_Food_By_Id(String id) throws Exception;

	FoodDetailsResponse create_Food(FoodDetailsRequestModel foodDetails);

	FoodDetailsResponse update_Food(String id, FoodDetailsRequestModel foodDetails) throws Exception;

	OperationStatusModel delete_Food(String id);

	List<FoodDetailsResponse> get_foods();
}
