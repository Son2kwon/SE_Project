package swengineering.team7.issuemanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WrongPriorityException extends RuntimeException {
    public WrongPriorityException(String message) {
        super(message);
    }
}
