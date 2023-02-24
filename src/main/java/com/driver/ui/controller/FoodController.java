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
		return foodService.get_Food_By_Id(id);
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
		return foodService.create_Food(foodDetails);
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		return foodService.update_Food(id,foodDetails);
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		return foodService.delete_Food(id);
	}
	
	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {
		return foodService.get_foods();
	}
}
