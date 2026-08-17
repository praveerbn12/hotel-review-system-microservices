package com.rating.service.RatingService.controller;

import com.rating.service.RatingService.entity.Rating;
import com.rating.service.RatingService.payload.RatingRequest;
import com.rating.service.RatingService.payload.RatingResponse;
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
    public ResponseEntity<RatingResponse> saveRating(@RequestBody RatingRequest ratingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.saveRating(ratingRequest));
    }

    @GetMapping("/allRating")
    public ResponseEntity<List<RatingResponse>> getAllRating() {
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllRating());
    }

    @GetMapping("/allRating/{userId}/user")
    public ResponseEntity<List<RatingResponse>> getAllRatingByUserId(@PathVariable Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllRatingByUserId(userId));
    }

    @GetMapping("/allRating/{hotelId}/hotel")
    public ResponseEntity<List<RatingResponse>> getAllRatingByHotelId(@PathVariable Integer hotelId) {
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllRatingByHotelId(hotelId));
    }
}
