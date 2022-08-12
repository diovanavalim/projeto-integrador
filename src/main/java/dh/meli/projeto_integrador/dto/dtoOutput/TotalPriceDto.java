package dh.meli.projeto_integrador.dto.dtoOutput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the TotalPriceDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the TotalPriceDto Class
 */
@Setter
/**
 * Method Constructor with all arguments implemented by Lombok lib
 */
@AllArgsConstructor
/**
 * Method builder implemented by Lombok lib
 */
@Builder
/**
 * Class used to create a Data Transfer Object for TotalPrice POJO
 * @author Gabriela Azevedo
 * @version 0.0.1
 * @see java.lang.Object
 */
public class TotalPriceDto {
    private Double totalPrice;
}
