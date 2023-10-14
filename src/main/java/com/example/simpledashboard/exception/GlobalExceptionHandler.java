package com.example.simpledashboard.exception;

import com.example.simpledashboard.common.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public Api exception(
            Exception exception
    ){
        log.error("GlobalExceptionHandler - exception : {}", exception);

        return Api.builder()
                .resultCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                .resultMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
    }
}
