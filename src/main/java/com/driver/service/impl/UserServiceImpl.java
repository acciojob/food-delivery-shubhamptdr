package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
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
            String str = UUID.randomUUID().toString();
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
}