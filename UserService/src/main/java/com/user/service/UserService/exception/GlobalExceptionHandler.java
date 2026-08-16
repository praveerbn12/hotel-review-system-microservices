package com.user.service.UserService.exception;

import com.user.service.UserService.payload.ApiResponse;
import jakarta.persistence.Entity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourseNotFound.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler( ResourseNotFound resourseNotFound){
          String m=resourseNotFound.getMessage();
         ApiResponse apiResponse  = ApiResponse.builder().message(m).success(false).httpStatus(HttpStatus.NOT_FOUND).build();
//         ApiResponse apiResponse= new ApiResponse(m,false,HttpStatus.NOT_FOUND);

        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneric(Exception ex) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Something went wrong")   // generic — never leak ex.getMessage() details to the client
                .success(false)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
