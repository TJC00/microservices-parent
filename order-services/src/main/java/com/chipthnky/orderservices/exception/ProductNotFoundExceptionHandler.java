package com.chipthnky.orderservices.exception;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductNotFoundExceptionHandler extends RuntimeException{
    private String message;

}
