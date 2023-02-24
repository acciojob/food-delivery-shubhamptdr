package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    FoodRepository foodRepository;
    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setFoodCategory(food.getFoodCategory());
        String str = usingRandomUUID();
        foodEntity.setFoodId(str);
        foodEntity.setFoodPrice(food.getFoodPrice());
        foodEntity.setFoodName(food.getFoodName());

        foodRepository.save(foodEntity);

        food.setFoodId(str);
        food.setId(foodRepository.findByFoodId(food.getFoodId()).getId());

        return food;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        FoodDto foodDto = new FoodDto();
        foodDto.setFoodId(foodEntity.getFoodId());
        foodDto.setFoodName(foodEntity.getFoodName());
        foodDto.setFoodPrice(foodEntity.getFoodPrice());
        foodDto.setId(foodEntity.getId());
        foodDto.setFoodCategory(foodEntity.getFoodCategory());
        return foodDto;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        //fetch
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        // set changes
        foodEntity.setFoodId(foodDetails.getFoodId());
        foodEntity.setFoodName(foodDetails.getFoodName());
        foodEntity.setFoodCategory(foodDetails.getFoodCategory());
        foodEntity.setFoodPrice(foodDetails.getFoodPrice());
        // save
        foodRepository.save(foodEntity);

        foodDetails.setFoodId(foodId);
        foodDetails.setId(foodEntity.getId());
        return foodDetails;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        long Id = foodRepository.findByFoodId(id).getId();
        foodRepository.deleteById(Id);
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodEntity> list = (List<FoodEntity>) foodRepository.findAll();
        List<FoodDto> foodDtoList = new ArrayList<>();
        for(FoodEntity f : list){
            FoodDto foodDto = new FoodDto();
            foodDto.setId(f.getId());
            foodDto.setFoodId(f.getFoodId());
            foodDto.setFoodPrice(f.getFoodPrice());
            foodDto.setFoodName(f.getFoodName());
            foodDto.setFoodCategory(f.getFoodCategory());

            foodDtoList.add(foodDto);
        }

        return foodDtoList;
    }

    /*-------------------------------------------------------------------------------*/

    @Override
    public FoodDetailsResponse get_Food_By_Id(String id) throws Exception {
        FoodDetailsResponse returnValue = new FoodDetailsResponse();

        FoodDto foodDto = getFoodById(id);

        returnValue.setFoodId(foodDto.getFoodId());
        returnValue.setFoodPrice(foodDto.getFoodPrice());
        returnValue.setFoodCategory(foodDto.getFoodCategory());
        returnValue.setFoodName(foodDto.getFoodName());

        return returnValue;

    }

    @Override
    public FoodDetailsResponse create_Food(FoodDetailsRequestModel foodDetails) {
        FoodDetailsResponse returnValue = new FoodDetailsResponse();

        FoodDto food = new FoodDto();
        food.setFoodName(foodDetails.getFoodName());
        food.setFoodCategory(foodDetails.getFoodCategory());
        food.setFoodPrice(foodDetails.getFoodPrice());

        FoodDto foodDto = createFood(food);

        returnValue.setFoodName(foodDto.getFoodName());
        returnValue.setFoodCategory(foodDto.getFoodCategory());
        returnValue.setFoodPrice(foodDto.getFoodPrice());
        returnValue.setFoodId(foodDto.getFoodId());

        return returnValue;

    }

    @Override
    public FoodDetailsResponse update_Food(String id, FoodDetailsRequestModel foodDetails) throws Exception {
        FoodDetailsResponse returnValue = new FoodDetailsResponse();

        FoodDto food = new FoodDto();
        food.setFoodName(foodDetails.getFoodName());
        food.setFoodCategory(foodDetails.getFoodCategory());
        food.setFoodPrice(foodDetails.getFoodPrice());

        FoodDto foodDto = updateFoodDetails(id,food);


        returnValue.setFoodName(foodDto.getFoodName());
        returnValue.setFoodId(foodDto.getFoodId());
        returnValue.setFoodPrice(foodDto.getFoodPrice());
        returnValue.setFoodCategory(foodDto.getFoodCategory());

        return returnValue;




    }

    @Override
    public OperationStatusModel delete_Food(String id) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.toString());
        try{
            deleteFoodItem(id);
        } catch (Exception e){
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.toString());
            return operationStatusModel;
        }

        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.toString());
        return operationStatusModel;
    }

    @Override
    public List<FoodDetailsResponse> get_foods() {
        List<FoodDto> foodDtoList = getFoods();
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

    static String usingRandomUUID() {

        UUID randomUUID = UUID.randomUUID();

        return randomUUID.toString().replaceAll("_", "");

    }
}