package com.driver.service;

import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.UserResponse;
import com.driver.shared.dto.UserDto;

/**
 * Handle exception cases for all methods which throw Exception
 */
public interface UserService{

	UserDto createUser(UserDto user) throws Exception;
	UserDto getUser(String email) throws Exception;
	UserDto getUserByUserId(String userId) throws Exception;
	UserDto updateUser(String userId, UserDto user) throws Exception;
	void deleteUser(String userId) throws Exception;
	List<UserDto> getUsers();

	UserResponse get_user(String id) throws Exception;

	UserResponse create_User(UserDetailsRequestModel userDetails) throws Exception;

	UserResponse update_User(String id, UserDetailsRequestModel userDetails) throws Exception;

	OperationStatusModel delete_User(String id);

	List<UserResponse> get_Users();

}
