package com.example.simpledashboard.exception;

import com.example.simpledashboard.common.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@Order(1)
public class ValidationExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Api validationException(
            MethodArgumentNotValidException exception
    ){
        log.error("ValidationExceptionHandler - exception : {}", exception);

        var errorMessageList = exception.getFieldErrors().stream()
                .map(x -> {
                    var format = "%s : { %s } 은 %s";
                    return String.format(format, x.getField(), x.getRejectedValue(), x.getDefaultMessage());
                })
                .collect(Collectors.toList());

        var error = Api.Error
                .builder()
                .errorMessage(errorMessageList)
                .build();

        return Api.builder()
                .resultCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .resultMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .error(error)
                .build();
    }
}
