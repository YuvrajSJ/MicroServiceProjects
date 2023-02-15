package com.rating.service.controller;

import com.rating.service.entity.Rating;
import com.rating.service.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;

    //Create rating
    @PostMapping
//    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
    }

    // Get rating by hotel id

    @GetMapping("/hotels/{hotelId}")
//    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    public ResponseEntity<List<Rating>> getByHotelId(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingsByHotelId(hotelId));
    }

    // Get rating by user id

    @GetMapping("/users/{userId}")
//    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    public ResponseEntity<List<Rating>> getByUserId(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingsByUserId(userId));
    }

    // Get all ratings

    @GetMapping
    public ResponseEntity<List<Rating>> getAll(){
        return ResponseEntity.ok(ratingService.getAllRatings());
    }
}
