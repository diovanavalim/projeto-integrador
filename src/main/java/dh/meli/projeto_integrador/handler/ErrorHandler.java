package dh.meli.projeto_integrador.handler;

import dh.meli.projeto_integrador.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Class that handle the exceptions that are thrown over the code execution flow.
 * @author Diovana Valim, Rafael Cavalcante
 * @version 0.0.1
 */
@ControllerAdvice
public class ErrorHandler {

    /**
     * Method that captures a InternalServerErrorException and build a response to send through HTTP request.
     * @param e instance of InternalServerErrorException class captured during the code execution flow.
     * @return a ResponseEntity containing details of the exception and a compatible HTTP status code.
     */
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ExceptionDetails> internalServerErrorHandler(InternalServerErrorException e) {
        return new ResponseEntity<ExceptionDetails>(ExceptionDetails
                .builder()
                .title("Internal Server Error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method that captures a ForbiddenException and build a response to send through HTTP request.
     * @param e instance of ForbiddenException class captured during the code execution flow.
     * @return a ResponseEntity containing details of the exception and a compatible HTTP status code.
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionDetails> forbiddenExceptionHandler(ForbiddenException e) {
        return new ResponseEntity<ExceptionDetails>(ExceptionDetails
                .builder()
                .title("Forbidden Operation")
                .status(HttpStatus.FORBIDDEN.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),
                HttpStatus.FORBIDDEN);
    }

    /**
     * Method that captures a ResourceNotFoundException and build a response to send through HTTP request.
     * @param e instance of ResourceNotFoundException class captured during the code execution flow.
     * @return a ResponseEntity containing details of the exception and a compatible HTTP status code.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetails> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        return new ResponseEntity<ExceptionDetails>(ExceptionDetails
                .builder()
                .title("Resource Not Found")
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Method that captures a InvalidParameterTypeException and build a response to send through HTTP request.
     * @param e instance of InvalidParameterTypeException class captured during the code execution flow.
     * @return a ResponseEntity containing details of the exception and a compatible HTTP status code.
     */
    @ExceptionHandler(InvalidParameterTypeException.class)
    public ResponseEntity<ExceptionDetails> invalidParameterTypeExceptionHandler(InvalidParameterTypeException e) {
        return new ResponseEntity<ExceptionDetails>(ExceptionDetails
                .builder()
                .title("Invalid Parameter Type")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),
                HttpStatus.BAD_REQUEST);
    }
}
