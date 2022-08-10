package dh.meli.projeto_integrador.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
/**
 * Class to customize the return of errors response.
 * @author Amanda Marinelli, Diovana Valim, Gabriela Azevedo, Rafael Cavalcante, Thiago Frozzi and Thiago Guimarães
 * @version 0.0.1
 */
public class ExceptionDetails {
    private String title;
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
