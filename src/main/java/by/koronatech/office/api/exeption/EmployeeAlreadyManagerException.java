package by.koronatech.office.api.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeAlreadyManagerException extends RuntimeException {
    public EmployeeAlreadyManagerException(String message) {
        super(message);
    }
}
