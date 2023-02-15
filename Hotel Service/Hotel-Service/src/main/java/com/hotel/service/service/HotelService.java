package com.hotel.service.service;

import com.hotel.service.entity.Hotel;

import java.util.List;

public interface HotelService {

    // Create
    Hotel create(Hotel hotel);

    // Get all hotels
    List<Hotel> getAllHotels();

    // Get hotel by id
    Hotel getHotel(String id);
}
