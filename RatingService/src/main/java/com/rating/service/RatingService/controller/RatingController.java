package com.rating.service.RatingService.controller;

import com.rating.service.RatingService.entity.Rating;
import com.rating.service.RatingService.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
    @Autowired
    public RatingService ratingService;
    @PostMapping("/addRating")
    public ResponseEntity<Rating> saveRating(@RequestBody Rating rating){
        Rating rating1=ratingService.saveRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating1);
    }

    @GetMapping("/allRating")
    public ResponseEntity<List<Rating>> getAllRating(){
        List<Rating> ratingList=ratingService.getAllRating();
        return  ResponseEntity.status(HttpStatus.OK).body(ratingList);
    }

    @GetMapping("/allRating/{userId}/user")
    public ResponseEntity<List<Rating>> getAllRatingByUserId(@PathVariable Integer userId){
        List<Rating> ratingList=ratingService.getAllRatingByUserId(userId);
        return  ResponseEntity.status(HttpStatus.OK).body(ratingList);
    }

    @GetMapping("/allRating/{hotelId}/hotel")
    public ResponseEntity<List<Rating>> getAllRatingByHotelId(@PathVariable Integer hotelId){
        List<Rating> ratingList=ratingService.getAllRatingByHotelId(hotelId);
        return  ResponseEntity.status(HttpStatus.OK).body(ratingList);
    }
}
