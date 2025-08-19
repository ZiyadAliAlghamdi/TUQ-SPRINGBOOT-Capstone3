package org.example.capstone3.Advice;

import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class ControllerAdvise {
    //handles any apiException
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> ApiException(ApiException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    //handles Jakarta Validation exceptions
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> MethodArgNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        String message = methodArgumentNotValidException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    //handles sql constrains exception
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> SQlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException){
        String message = sqlIntegrityConstraintViolationException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }


    //method not allowed
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> methodNotAllowed(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException){
        String message = httpRequestMethodNotSupportedException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }


    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> MethodArgsTypeMismatch(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException){
        String message = methodArgumentTypeMismatchException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }
}
