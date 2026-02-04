package com.rating.service.RatingService.service;

import com.rating.service.RatingService.entity.Rating;

import java.util.List;

public interface RatingService {
      Rating saveRating(Rating rating);
      List<Rating> getAllRating();
      List<Rating> getAllRatingByUserId(Integer userId);
      List<Rating> getAllRatingByHotelId(Integer hotelId);
}
