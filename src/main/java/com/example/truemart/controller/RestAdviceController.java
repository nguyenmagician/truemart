package com.example.truemart.controller;

import com.example.truemart.model.ErrorCustom;
import com.example.truemart.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestAdviceController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorMessage> handleNotVaildException(MethodArgumentNotValidException ex) {
        List<ErrorCustom> list = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(err -> {
            String field = err.getField();
            String message = err.getDefaultMessage();

            list.add(new ErrorCustom(field,message));
        });


        return new ResponseEntity<>(new ErrorMessage(list), HttpStatus.BAD_REQUEST);
    }
}
