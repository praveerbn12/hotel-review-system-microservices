package com.user.service.UserService.exception;

public class ResourseNotFound extends RuntimeException{
    public ResourseNotFound(){
        super("Resource not found ");
    }
    public ResourseNotFound(String msg){
        super("Resource not found :" + msg);
    }
}

