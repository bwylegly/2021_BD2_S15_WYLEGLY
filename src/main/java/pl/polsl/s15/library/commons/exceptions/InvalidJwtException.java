package pl.polsl.s15.library.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}