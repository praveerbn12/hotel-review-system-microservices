package com.rating.service.RatingService.service;

import com.rating.service.RatingService.entity.Rating;
import com.rating.service.RatingService.payload.RatingRequest;
import com.rating.service.RatingService.payload.RatingResponse;

import java.util.List;

public interface RatingService {
      RatingResponse saveRating(RatingRequest ratingRequest);
      List<RatingResponse> getAllRating();
      List<RatingResponse> getAllRatingByUserId(Integer userId);
      List<RatingResponse> getAllRatingByHotelId(Integer hotelId);
}
