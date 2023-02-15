package com.hotel.service.service.impl;

import com.hotel.service.entity.Hotel;
import com.hotel.service.exception.ResourceNotFoundException;
import com.hotel.service.repository.HotelRepository;
import com.hotel.service.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;
    @Override
    public Hotel create(Hotel hotel) {
        String hotelId=UUID.randomUUID().toString();
        hotel.setId(hotelId);
        Hotel hotelEntity=hotelRepository.save(hotel);
        return hotelEntity;
    }

    @Override
    public List<Hotel> getAllHotels() {
        List<Hotel> hotels=hotelRepository.findAll();
        return hotels;
    }

    @Override
    public Hotel getHotel(String id) {
        Hotel hotel= hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel with given Id not found "+id));
        return hotel;
    }
}
