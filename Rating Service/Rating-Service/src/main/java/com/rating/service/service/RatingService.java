package com.rating.service.service;

import com.rating.service.entity.Rating;

import java.util.List;

public interface RatingService {

    // Create
    Rating create(Rating hotel);

    // Get all hotels
    List<Rating> getAllRatings();

    // Get ratings by hotel id
    List<Rating> getRatingsByHotelId(String hotelId);

    // Get ratings by user id
    List<Rating> getRatingsByUserId(String userId);
}
