package com.hotel.service.controller;

import com.hotel.service.entity.Hotel;
import com.hotel.service.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    HotelService hotelService;

    //Create hotel
    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Hotel> create(@RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
    }

    // Get hotel by id

    @GetMapping("/{hotelId}")
    @PreAuthorize("hasAuthority('SCOPE_internal')")
    public ResponseEntity<Hotel> get(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotel(hotelId));
    }

    // Get all hotels

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    public ResponseEntity<List<Hotel>> getAll(){
        return ResponseEntity.ok(hotelService.getAllHotels());
    }
}