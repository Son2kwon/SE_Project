package swengineering.team7.issuemanagementsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import swengineering.team7.issuemanagementsystem.exception.ProjectNotFoundException;
import swengineering.team7.issuemanagementsystem.exception.UserNotFoundException;
import swengineering.team7.issuemanagementsystem.exception.WrongPriorityException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WrongPriorityException.class)
    public ResponseEntity<?> resourceNotFoundException(WrongPriorityException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<?> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(UserNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
