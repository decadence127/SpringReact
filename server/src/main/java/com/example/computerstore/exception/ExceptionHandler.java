package com.example.computerstore.exception;

public class ExceptionHandler extends RuntimeException{
    private Integer status;

    public ExceptionHandler(Integer status, String message){
        super(message);
        this.status = status;
    }

}
