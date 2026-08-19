package com.user.service.UserService.service;

import com.user.service.UserService.entity.User;
import com.user.service.UserService.payload.*;

import java.util.List;

public interface UserService {
    //create user
    UserResponse saveUser(UserRequest userRequest);
    List<UserResponse> getAllUser();
    UserResponse getUserByUserId(Integer userId);

    UserResponse register(RegisterRequest req);

    LoginResponse login(LoginRequest req) throws Exception;
    //Update and delete
}
