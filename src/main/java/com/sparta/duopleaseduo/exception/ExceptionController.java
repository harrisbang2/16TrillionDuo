package com.sparta.duopleaseduo.exception;

import com.sparta.duopleaseduo.dto.response.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDto> entityNotFoundExceptionHandler(EntityNotFoundException entityNotFoundException){
        return createResponse(HttpStatus.BAD_REQUEST, entityNotFoundException.getMessage());
    }

    private ResponseEntity<ExceptionDto> createResponse(HttpStatus status, String message){
        return ResponseEntity.status(status.value())
                .body(new ExceptionDto(status.value(), status, message));
    }
}
