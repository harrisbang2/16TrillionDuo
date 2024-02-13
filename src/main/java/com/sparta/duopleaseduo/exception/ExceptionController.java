package com.sparta.duopleaseduo.exception;

import com.sparta.duopleaseduo.dto.response.ExceptionDto;
import com.sparta.duopleaseduo.exception.feed.AlreadyLikedException;
import com.sparta.duopleaseduo.exception.feed.UserNotMatchException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDto> entityNotFoundExceptionHandler(EntityNotFoundException e){
        return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AlreadyLikedException.class)
    public ResponseEntity<ExceptionDto> alreadyLikedExceptionHandler(AlreadyLikedException e){
        return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UserNotMatchException.class)
    public ResponseEntity<ExceptionDto> userNotMatchExceptionHandler(UserNotMatchException e){
        return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<ExceptionDto> createResponse(HttpStatus status, String message){
        return ResponseEntity.status(status.value())
                .body(new ExceptionDto(status.value(), status, message));
    }
}
