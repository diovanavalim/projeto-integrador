package dh.meli.projeto_integrador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class that extends of the RunTimeException error class. It represents the custom Exception 404
 * ResourceNotFoundException
 * @author Diovana Valim, Rafael Cavalcante
 * @version 0.0.1
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor method called whether an operation was not able to find valid results.
     * @param message custom message displayed to user by the server response.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
