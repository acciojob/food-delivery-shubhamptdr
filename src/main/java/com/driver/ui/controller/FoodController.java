package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.*;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
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
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	FoodService foodService;
	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
		FoodDetailsResponse returnValue = new FoodDetailsResponse();

		FoodDto foodDto = foodService.getFoodById(id);
		BeanUtils.copyProperties(foodDto, returnValue);
		return returnValue;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
		FoodDetailsResponse returnValue = new FoodDetailsResponse();

		FoodDto food = new FoodDto();
		BeanUtils.copyProperties(foodDetails,food);

		FoodDto foodDto = foodService.createFood(food);
		BeanUtils.copyProperties(foodDto, returnValue);
		return returnValue;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDetailsResponse returnValue = new FoodDetailsResponse();

		FoodDto food = new FoodDto();
		BeanUtils.copyProperties(foodDetails,food);

		FoodDto foodDto = foodService.updateFoodDetails(id,food);
		BeanUtils.copyProperties(foodDto, returnValue);
		return returnValue;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName(RequestOperationName.DELETE.toString());
		try{
			foodService.deleteFoodItem(id);
		} catch (Exception e){
			operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.toString());
			return operationStatusModel;
		}

		operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.toString());
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {
		List<FoodDto> foodDtoList = foodService.getFoods();
		List<FoodDetailsResponse> foodDetailsResponseList = new ArrayList<>();
		for(FoodDto f : foodDtoList){

			FoodDetailsResponse dto =  new FoodDetailsResponse();
			dto.setFoodId(f.getFoodId());
			dto.setFoodName(f.getFoodName());
			dto.setFoodCategory(f.getFoodCategory());
			dto.setFoodPrice(f.getFoodPrice());

			foodDetailsResponseList.add(dto);

		}
		return foodDetailsResponseList;

	}
}
