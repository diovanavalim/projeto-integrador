package dh.meli.projeto_integrador.dto.dtoOutput;

import dh.meli.projeto_integrador.model.Batch;
import dh.meli.projeto_integrador.model.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the ProductStockDto Class
 */
@Getter

/**
 * Method Setter implemented by Lombok lib for set access the private attributes of the ProductStockDto Class
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
public class ProductStockDto {
    private Long id;
    private String section;
    private String name;
    private List<BatchDto> batchStockDto = new ArrayList<>();

    public ProductStockDto(Product product, List<Batch> batchList) {
        this.name = product.getName();
        this.id = product.getId();
        this.section = product.getType();

        for (Batch b : batchList) {
            long idWarehouse = (b.getOrderEntry().getSection().getWarehouse().getId());
            this.batchStockDto.add(new BatchDto(idWarehouse, b.getId(), b.getCurrentQuantity(), b.getDueDate()));
        }
    }
}

