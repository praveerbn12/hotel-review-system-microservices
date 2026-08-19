package com.user.service.UserService.service.impl;

import com.user.service.UserService.client.RatingClient;
import com.user.service.UserService.entity.User;
import com.user.service.UserService.enums.Role;
import com.user.service.UserService.exception.ResourseNotFound;
import com.user.service.UserService.payload.*;
import com.user.service.UserService.repository.UserRepository;
import com.user.service.UserService.security.JwtService;
import com.user.service.UserService.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RatingClient ratingClient;
    @Autowired private JwtService jwtService;


    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserResponse saveUser(UserRequest userRequest) {

        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setUserEmail(userRequest.getUserEmail());
        user.setUserAbout(userRequest.getUserAbout());

        User saved= userRepository.save(user);
        return toResponse(saved, null);
    }

//    @Override
//    public List<User> getAllUser() {
//        return userRepository.findAll();
//    }

    @Override
    public List<UserResponse> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(u -> toResponse(u, null))
                .toList();
    }

    @Override
    public UserResponse getUserByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourseNotFound("User with id " + userId + " not found"));

        List<RatingDto> ratings = ratingClient.getRatingsByUser(userId);
        return toResponse(user, ratings);
    }

    private UserResponse toResponse(User user, List<RatingDto> ratings) {
        UserResponse res = new UserResponse();
        res.setUserId(user.getUserId());
        res.setUserName(user.getUserName());
        res.setUserEmail(user.getUserEmail());
        res.setUserAbout(user.getUserAbout());
        res.setRatingDtoList(ratings);
        return res;
    }


    @Override
    public UserResponse register(RegisterRequest req) {
        User user = new User();
        user.setUserName(req.getUserName());
        user.setUserEmail(req.getUserEmail());
        user.setUserAbout(req.getUserAbout());
        user.setPassword(passwordEncoder.encode(req.getPassword()));  // HASH — never store raw
        user.setRole(Role.ROLE_USER);                                    // default role
        User saved = userRepository.save(user);
        return toResponse(saved, null);
    }



    @Override
    public LoginResponse login(LoginRequest req) throws Exception {
        User user = userRepository.findByUserEmail(req.getUserEmail())
                .orElseThrow(() -> new ResourseNotFound("Invalid email or password"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new ResourseNotFound("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getUserId(), user.getRole().name());
        return new LoginResponse(token);
    }

//    @Override
//    public User getUserByUserId(Integer userId) {
//        User user=userRepository.findById(userId).orElseThrow(()-> new ResourseNotFound("User Id not Present"));
////        ArrayList<RatingDto> forobj= restTemplate.getForObject("http://localhost:8083/api/rating/allRating/"+userId+"/user", ArrayList.class);
////        logger.info("obj ",forobj);
////        user.setRatingDtoList(forobj);
//
//        List<RatingDto> ratings = ratingClient.getRatingsByUser(userId);
//        user.setRatingDtoList(ratings);
//        return  user;
//    }
}
