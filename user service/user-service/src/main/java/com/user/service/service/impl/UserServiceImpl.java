package com.user.service.service.impl;

import com.user.service.entity.Hotel;
import com.user.service.entity.Rating;
import com.user.service.entity.UserEntity;
import com.user.service.exception.ResourceNotFoundException;
import com.user.service.external.services.HotelService;
import com.user.service.repository.UserRepository;
import com.user.service.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public UserEntity saveUser(UserEntity user) {
        String randomUserId= UUID.randomUUID().toString();
        user.setId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(String id) {
      UserEntity user=userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with given Id not found on server "+id));
      //Calling rating service to get ratings data, WHILE USING SERVICE NAME INSTEAD OF HOST AND PORT, WE NEED TO PUT @LoadBalanced on RestTemplate bean
        Rating[] ratingsOfUser=restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getId(), Rating[].class);
        logger.info("{}",ratingsOfUser);
        List<Rating> ratings= Arrays.stream(ratingsOfUser).toList();
        // Getting hotels data
        List<Rating> ratingList= ratings.stream().map(rating ->{

            //calling hotel service using rest template
//            ResponseEntity<Hotel> forEntity =restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//            Hotel hotel=forEntity.getBody();
//            logger.info("response status code: {}", forEntity.getStatusCode());

            //calling hotel service using FeingClient, first need to add dependency then user Enable annotation on SpringBootApp
            // then create an interface with @FeignClient annotation, now here will call the interface method whose implementation
            // will be provided by spring boot at run time automatically.
            Hotel hotel=hotelService.getHotel(rating.getHotelId());

            //set hotel to ratings
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);
      return user;
    }
}
