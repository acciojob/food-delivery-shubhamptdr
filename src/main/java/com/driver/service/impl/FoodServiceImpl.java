package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
class FoodServiceImpl implements FoodService {
    @Autowired
    FoodRepository foodRepository;
    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setFoodCategory(food.getFoodCategory());
        String str = UUID.randomUUID().toString();
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
        foodDetails.setFoodId(foodId);
        foodDetails.setId(foodEntity.getId());
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
}