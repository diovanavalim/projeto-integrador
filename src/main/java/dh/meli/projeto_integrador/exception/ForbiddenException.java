package dh.meli.projeto_integrador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class that extends of the RunTimeException error class. It represents the custom Exception 403 ForbiddenException.
 * @author Diovana Valim
 * @version 0.0.1
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    /**
     * Constructor method called whether an operation hasn't the necessary authorization to execute.
     * @param message custom message displayed to user by the server response.
     */
    public ForbiddenException(String message) {
        super(message);
    }
}
