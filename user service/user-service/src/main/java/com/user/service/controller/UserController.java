package com.user.service.controller;

import com.user.service.entity.UserEntity;
import com.user.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    Logger logger= LoggerFactory.getLogger(UserController.class);
    // create user
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user){
        UserEntity userEntity=userService.saveUser(user);
        return  ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
    }

    // Get user by id
int retry=1;
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelCircuitBreaker", fallbackMethod = "ratingHotelFallBack")
//    @Retry(name = "ratingHotelRetry", fallbackMethod = "ratingHotelFallBack")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<UserEntity> getUser(@PathVariable String userId){

        logger.info("retry : {}",retry);
        retry++;
        if(retry==3){retry=0;}
        UserEntity user=userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // Creating fallback method for circuitbreaker
    public ResponseEntity<UserEntity> ratingHotelFallBack(String userId, Exception ex){
        logger.info("Fallback is executed because service is dpwn : ", ex.getMessage());
        UserEntity user= UserEntity.builder().name("Dummy").email("dummay@exa,ple.com").about("This user is created dummy because some service is down").id("12345").build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity> allUsers=userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
