package com.devp.springmongocrud.exception;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Getter
public class ErrorDetails {

    private Date timeStamp;
    private String message;
    private String details;

    public ErrorDetails(Date timeStamp, String message, String details) {
        super();
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }



}
