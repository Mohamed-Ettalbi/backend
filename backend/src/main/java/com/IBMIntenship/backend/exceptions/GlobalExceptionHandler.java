package com.IBMIntenship.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestApiServerException.class)
    public ResponseEntity<CustomErrorResponse> handleServerException(RestApiServerException ex) {
        return ResponseEntity.status(ex.getErrorResponse().getStatusCode())
                .body(ex.getErrorResponse());
    }

    @ExceptionHandler(RestApiClientException.class)
    public ResponseEntity<CustomErrorResponse> handleClientException(RestApiClientException ex) {
        return ResponseEntity.status(ex.getErrorResponse().getStatusCode())
                .body(ex.getErrorResponse());
    }


    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException ex) {
        return new ResponseEntity<>("Invalid Token: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InsufficientRoleException.class)
    public ResponseEntity<String> handleInsufficientRoleException(InsufficientRoleException ex) {
        return new ResponseEntity<>("Insufficient Role: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        return new ResponseEntity<>("Unauthorized Access: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(GroupIdNullException.class)
    public ResponseEntity<String> handleUnauthorizedAccessException(GroupIdNullException ex) {
        return new ResponseEntity<>("Group Id Is Null : " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

