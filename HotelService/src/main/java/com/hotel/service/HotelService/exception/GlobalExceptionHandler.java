package com.hotel.service.HotelService.exception;

import com.hotel.service.HotelService.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFound resourseNotFound){
        String m=resourseNotFound.getMessage();
        ApiResponse apiResponse  = ApiResponse.builder().message(m).success(true).httpStatus(HttpStatus.NOT_FOUND).build();
//         ApiResponse apiResponse= new ApiResponse(m,false,HttpStatus.NOT_FOUND);

        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);

    }
}
