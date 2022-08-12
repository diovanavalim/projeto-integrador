package dh.meli.projeto_integrador.dto.dtoOutput;

import lombok.*;

import java.util.List;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of GetBatchDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of GetBatchDto Class
 */
@Setter
/**
 * Method Default Constructor implemented by Lombok lib
 */
@NoArgsConstructor
/**
 * Method Constructor with all arguments implemented by Lombok lib
 */
@AllArgsConstructor
/**
 * Method builder implemented by Lombok lib
 */
@Builder
/**
 * Class used to create a Data Transfer Output Object to return a batch stock
 * @author Diovana Valim
 * @version 0.0.1
 * @see java.lang.Object
 */
public class GetBatchDto {
    private List<BatchStockDto> batchStockDto;
}
