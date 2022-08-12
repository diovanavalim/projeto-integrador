package dh.meli.projeto_integrador.dto.dtoOutput;

import dh.meli.projeto_integrador.model.Batch;
import lombok.*;

import java.time.LocalDate;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of BatchStockDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of BatchStockDto Class
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
 * Class used to create a Data Transfer Output Object for Batch POJO
 * @author Diovana Valim
 * @version 0.0.1
 * @see java.lang.Object
 */
public class BatchStockDto {
    private long batchNumber;

    private long productId;

    private String productType;

    private LocalDate dueDate;

    private int quantity;

    public BatchStockDto(Batch batch) {
        this.batchNumber = batch.getId();
        this.productId = batch.getProduct().getId();
        this.productType = batch.getProduct().getType();
        this.dueDate = batch.getDueDate();
        this.quantity = batch.getCurrentQuantity();
    }
}
