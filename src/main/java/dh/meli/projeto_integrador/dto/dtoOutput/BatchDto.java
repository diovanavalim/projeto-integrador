package dh.meli.projeto_integrador.dto.dtoOutput;

import lombok.*;

import java.time.LocalDate;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the BatchDto Class
 */
@Getter

/**
 * Method Setter implemented by Lombok lib for set access the private attributes of the BatchDto Class
 */
@Setter
/**
 * Method builder implemented by Lombok lib
 */
@Builder
/**
 * Method Constructor with all arguments implemented by Lombok lib
 */
@AllArgsConstructor
/**
 * Method Default Constructor implemented by Lombok lib
 */
@NoArgsConstructor

/**
 * Class used to create a Data Transfer Object for ProductService.getProductBatchProps method
 * @author Amanda Marinelli, Thiago Almeida
 * @version 0.0.1
 * @see java.lang.Object
 */
public class BatchDto {
    private long idWarehouse;
    private long batchNumber;
    private int currentQuantity;
    private LocalDate dueDate;
}


