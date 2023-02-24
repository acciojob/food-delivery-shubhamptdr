package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.model.response.UserResponse;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
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
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
		UserResponse returnValue = new UserResponse();

		UserDto user = userService.getUserByUserId(id);
		BeanUtils.copyProperties(user, returnValue);

		return returnValue;

	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserResponse returnValue = new UserResponse();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails,userDto);

		UserDto user = userService.createUser(userDto);
		BeanUtils.copyProperties(user, returnValue);

		return returnValue;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserResponse returnValue = new UserResponse();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails,userDto);

		UserDto user = userService.updateUser(id,userDto);
		BeanUtils.copyProperties(user, returnValue);

		return returnValue;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{


		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName(RequestOperationName.DELETE.toString());
		try {
			userService.deleteUser(id);
		}catch (Exception e){
			operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.toString());
			return operationStatusModel;
		}
		operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.toString());

		return operationStatusModel;
	}
	
	@GetMapping()
	public List<UserResponse> getUsers(){
		List<UserResponse> userResponseList = new ArrayList<>();
		List<UserDto> userDtoList = userService.getUsers();
		for (UserDto userDto : userDtoList){
			UserResponse userResponse = new UserResponse();
			userResponse.setUserId(userDto.getUserId());
			userResponse.setEmail(userDto.getEmail());
			userResponse.setFirstName(userDto.getFirstName());
			userResponse.setLastName(userDto.getLastName());

			userResponseList.add(userResponse);
		}

		return userResponseList;
	}
	
}
