package com.user.service.UserService.client;


import com.user.service.UserService.payload.RatingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATINGSERVICE")
public interface RatingClient {
    @GetMapping("/api/rating/allRating/{userId}/user")
    List<RatingDto> getRatingsByUser(@PathVariable Integer userId);
}
