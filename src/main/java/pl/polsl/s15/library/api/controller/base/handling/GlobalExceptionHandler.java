package pl.polsl.s15.library.api.controller.base.handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.polsl.s15.library.api.controller.base.response.ErrorResponseDTO;
import pl.polsl.s15.library.commons.exceptions.BadRequestException;
import pl.polsl.s15.library.commons.exceptions.InvalidRequestException;
import pl.polsl.s15.library.commons.exceptions.authentication.InvalidJwtException;
import pl.polsl.s15.library.commons.exceptions.reservations.BooksUnavailableException;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAnyException(Exception exception) {
        log.error(exception.toString());
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.errorResponseBuilder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Internal server error!")
                .timestamp(new Date())
                .errorCause(exception.getMessage())
                .build();
        return prepareErrorResponse(errorResponseDTO);
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidJwtException(InvalidJwtException ex) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.errorResponseBuilder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Request contains invalid JWT")
                .timestamp(new Date())
                .errorCause(ex.getMessage())
                .build();
        return prepareErrorResponse(errorResponseDTO);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.errorResponseBuilder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("User specified in token does not exist!")
                .timestamp(new Date())
                .errorCause(ex.getMessage())
                .build();
        return prepareErrorResponse(errorResponseDTO);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidRequestException(InvalidRequestException ex) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.errorResponseBuilder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Bad request")
                .timestamp(new Date())
                .errorCause(ex.getMessage())
                .build();
        return prepareErrorResponse(errorResponseDTO);
    }

    @ExceptionHandler(BooksUnavailableException.class)
    public ResponseEntity<ErrorResponseDTO> handleBooksUnavailableException(BooksUnavailableException ex) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.errorResponseBuilder()
                .status(HttpStatus.CONFLICT)
                .message("Books unavailable")
                .timestamp(new Date())
                .errorCause(ex.getMessage())
                .build();
        return prepareErrorResponse(errorResponseDTO);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequestException(BadRequestException ex) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.errorResponseBuilder()
                .status(HttpStatus.BAD_REQUEST)
                .message("BAD REQUEST!")
                .timestamp(new Date())
                .errorCause(ex.getMessage())
                .build();
        return prepareErrorResponse(errorResponseDTO);
    }
}
