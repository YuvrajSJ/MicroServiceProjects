package com.user.service.exception;

import com.user.service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
//        ex.getMessage();
//        ApiResponse response=ApiResponse.builder().message(ex.getMessage()).success(true).status(HttpStatus.NOT_FOUND).build();
//        return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
//    }

    // or by using Map instead of ApiResponse model, as below
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> notFoundHandler(ResourceNotFoundException ex){
        Map map= new HashMap();
        map.put("message", ex.getMessage());
        map.put("success", false);
        map.put("status", HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }
}
