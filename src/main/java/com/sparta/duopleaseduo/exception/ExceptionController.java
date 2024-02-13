package com.sparta.duopleaseduo.exception;

import com.sparta.duopleaseduo.exception.feed.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundExceptionHandler(EntityNotFoundException entityNotFoundException){
        return ResponseEntity.status(404).body(entityNotFoundException.getMessage());
    }
}
