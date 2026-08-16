package com.user.service.UserService.service;

import com.user.service.UserService.entity.User;
import com.user.service.UserService.payload.UserRequest;
import com.user.service.UserService.payload.UserResponse;

import java.util.List;

public interface UserService {
    //create user
    UserResponse saveUser(UserRequest userRequest);
    List<UserResponse> getAllUser();
    UserResponse getUserByUserId(Integer userId);
    //Update and delete
}
