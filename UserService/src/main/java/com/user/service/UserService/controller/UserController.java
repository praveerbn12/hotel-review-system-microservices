package com.user.service.UserService.controller;

import com.user.service.UserService.entity.User;
import com.user.service.UserService.payload.UserRequest;
import com.user.service.UserService.payload.UserResponse;
import com.user.service.UserService.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/addUser")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserResponse saved = userService.saveUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/getUser")
    public ResponseEntity<List<UserResponse>> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserId(userId));
    }

}
