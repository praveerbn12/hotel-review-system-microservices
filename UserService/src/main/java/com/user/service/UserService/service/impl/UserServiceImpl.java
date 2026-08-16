package com.user.service.UserService.service.impl;

import com.user.service.UserService.client.RatingClient;
import com.user.service.UserService.entity.User;
import com.user.service.UserService.exception.ResourseNotFound;
import com.user.service.UserService.payload.RatingDto;
import com.user.service.UserService.payload.UserRequest;
import com.user.service.UserService.payload.UserResponse;
import com.user.service.UserService.repository.UserRepository;
import com.user.service.UserService.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RatingClient ratingClient;

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
