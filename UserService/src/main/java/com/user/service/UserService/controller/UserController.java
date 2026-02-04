package com.user.service.UserService.controller;

import com.user.service.UserService.entity.User;
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
    public ResponseEntity<User> createUser(@RequestBody  User user){
        System.out.println("in controller " + user);
        User user1=userService.saveUser(user);
        System.out.println(user1);
        return  ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    @GetMapping("/getUser")
    public ResponseEntity<List<User>> getAllUser(){
      List<User>  user1=userService.getAllUser();
        return  ResponseEntity.status(HttpStatus.OK).body(user1);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId ){
        User  user1=userService.getUserByUserId(userId);
        return  ResponseEntity.status(HttpStatus.OK).body(user1);
    }

}
