package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.model.response.UserResponse;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto user) throws Exception {
        try {
            UserEntity userEntity = new UserEntity();
            String str = usingRandomUUID();
            userEntity.setEmail(user.getEmail());
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setUserId(str);
            userRepository.save(userEntity);

        }catch (Exception e){
            throw new Exception("user exists");
        }

        user.setId(userRepository.findByEmail(user.getEmail()).getId());
        user.setUserId(userRepository.findByEmail(user.getEmail()).getUserId());

        return user;
    }

    @Override
    public UserDto getUser(String email) throws Exception {
        // fetch userEntity
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null){
            throw new Exception("user not found");
        }
        // set attribute wrt to UserDto
        UserDto userDto = new UserDto();
        userDto.setUserId(userEntity.getUserId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setId(userEntity.getId());


        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        // fetch userEntity
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null){
            throw new Exception("user not found");
        }
        // set attribute wrt to UserDto
        UserDto userDto = new UserDto();
        userDto.setUserId(userEntity.getUserId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setId(userEntity.getId());

        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null){
            throw new Exception("User not found");
        }
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        userRepository.save(userEntity);

        user.setUserId(userEntity.getUserId());
        user.setId(userEntity.getId());
        return user;
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        long id  = userRepository.findByUserId(userId).getId();
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> list = (List<UserEntity>) userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(UserEntity u : list){
            UserDto userDto = new UserDto();
            userDto.setId(u.getId());
            userDto.setUserId(u.getUserId());
            userDto.setFirstName(u.getFirstName());
            userDto.setLastName(u.getLastName());
            userDto.setEmail(u.getEmail());

            userDtoList.add(userDto);
        }
        return userDtoList;

    }

    /*----------------------------------------------------------*/

    public UserResponse get_user(String id) throws Exception {
        UserResponse returnValue = new UserResponse();

        UserDto user = getUserByUserId(id);
        returnValue.setUserId(user.getUserId());
        returnValue.setEmail(user.getEmail());
        returnValue.setFirstName(user.getFirstName());
        returnValue.setLastName(user.getLastName());

        return returnValue;
    }

    public UserResponse create_User(UserDetailsRequestModel userDetails) throws Exception {
        UserResponse returnValue = new UserResponse();

        UserDto userDto = new UserDto();
        userDto.setFirstName(userDetails.getFirstName());
        userDto.setLastName(userDetails.getLastName());
        userDto.setEmail(userDetails.getEmail());

        UserDto user = createUser(userDto);

        returnValue.setUserId(user.getUserId());
        returnValue.setEmail(user.getEmail());
        returnValue.setFirstName(user.getFirstName());
        returnValue.setLastName(user.getLastName());


        return returnValue;

    }

    public UserResponse update_User(String id, UserDetailsRequestModel userDetails) throws Exception {
        UserResponse returnValue = new UserResponse();

        UserDto userDto = new UserDto();
        userDto.setFirstName(userDetails.getFirstName());
        userDto.setLastName(userDetails.getLastName());
        userDto.setEmail(userDetails.getEmail());

        String userId = userRepository.findByUserId(id).getUserId();
        UserDto user = updateUser(userId,userDto);
        returnValue.setUserId(user.getUserId());
        returnValue.setEmail(user.getEmail());
        returnValue.setFirstName(user.getFirstName());
        returnValue.setLastName(user.getLastName());

        return returnValue;

    }

    public OperationStatusModel delete_User(String id) {

        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.toString());
        try {
            deleteUser(id);
        }catch (Exception e){
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.toString());
            return operationStatusModel;
        }
        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.toString());

        return operationStatusModel;
    }

    public List<UserResponse> get_Users() {
        List<UserResponse> userResponseList = new ArrayList<>();
        List<UserDto> userDtoList = getUsers();
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

    static String usingRandomUUID() {

        UUID randomUUID = UUID.randomUUID();

        return randomUUID.toString().replaceAll("_", "");

    }
}