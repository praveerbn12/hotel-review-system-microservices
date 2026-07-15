package com.user.service.UserService.service.impl;

import com.user.service.UserService.client.RatingClient;
import com.user.service.UserService.entity.User;
import com.user.service.UserService.exception.ResourseNotFound;
import com.user.service.UserService.payload.RatingDto;
import com.user.service.UserService.repository.UserRepository;
import com.user.service.UserService.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
    public User saveUser(User user) {
        System.out.println(user);
        User user1= userRepository.save(user);
        System.out.println(user1);
        return user1;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUserId(Integer userId) {
        User user=userRepository.findById(userId).orElseThrow(()-> new ResourseNotFound("User Id not Present"));
//        ArrayList<RatingDto> forobj= restTemplate.getForObject("http://localhost:8083/api/rating/allRating/"+userId+"/user", ArrayList.class);
//        logger.info("obj ",forobj);
//        user.setRatingDtoList(forobj);

        List<RatingDto> ratings = ratingClient.getRatingsByUser(userId);
        user.setRatingDtoList(ratings);
        return  user;
    }
}
