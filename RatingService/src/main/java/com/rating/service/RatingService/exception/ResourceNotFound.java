package com.rating.service.RatingService.exception;

public class ResourceNotFound extends  RuntimeException{

    public ResourceNotFound(){
        super("Resource Not Found");
    }
    public ResourceNotFound(String message){
        super("Resource Not Found" + message);
    }
}
