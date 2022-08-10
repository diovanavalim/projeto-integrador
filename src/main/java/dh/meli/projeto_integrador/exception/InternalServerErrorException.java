package dh.meli.projeto_integrador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class that extends of the RunTimeException error class. It represents the custom Exception 500
 * InternalServerErrorException.
 * @author Diovana Valim
 * @version 0.0.1
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {

    /**
     * Constructor method called whether an operation results in an internal server error.
     * @param message custom message displayed to user by the server response.
     */
    public InternalServerErrorException (String message) {
        super(message);
    }
}
