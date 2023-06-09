package com.chipthnky.orderservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdviceHandler {
    @ExceptionHandler(ProductNotFoundExceptionHandler.class)
    public ProblemDetail productNotFoundExceptionHandler(ProductNotFoundExceptionHandler e){

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Product Not Found");

        return problemDetail;
    }
}
