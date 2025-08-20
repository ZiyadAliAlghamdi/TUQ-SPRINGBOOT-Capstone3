package org.example.capstone3.Advice;

import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Api.ApiResponse;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.PlaceholderResolutionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.net.http.HttpClient;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

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


    @ExceptionHandler(value = PlaceholderResolutionException.class)
    public ResponseEntity<?> PlaceholderResolutionException(PlaceholderResolutionException placeholderResolutionException){
        String message = placeholderResolutionException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<?> IOException(IOException ioException){
        String message = ioException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> DataIntegrityViolationException( DataIntegrityViolationException dataIntegrityViolationException){
        String message = dataIntegrityViolationException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<?> NullPointerException(NullPointerException NullPointerException){
        String message = NullPointerException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<?> SQLSyntaxErrorException(SQLSyntaxErrorException SQLSyntaxErrorException){
        String message = SQLSyntaxErrorException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<?> HttpClientErrorException(HttpClientErrorException HttpClientErrorException){
        String message = HttpClientErrorException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = CommandAcceptanceException.class)
    public ResponseEntity<?> CommandAcceptanceException(CommandAcceptanceException CommandAcceptanceException){
        String message = CommandAcceptanceException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }
}
