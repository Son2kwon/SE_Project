package swengineering.team7.issuemanagementsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import swengineering.team7.issuemanagementsystem.exception.WrongPriorityException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WrongPriorityException.class)
    public ResponseEntity<?> resourceNotFoundException(WrongPriorityException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


}
