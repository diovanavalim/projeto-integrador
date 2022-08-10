package dh.meli.projeto_integrador.dto.dtoOutput;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the
 * TotalProductByWarehouseDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the
 * TotalProductByWarehouseDto Class
 */
@Setter
/**
 * Method Constructor with all arguments implemented by Lombok lib
 */
@AllArgsConstructor
/**
 * Class used to create a Data Transfer Object for TotalProductByWarehouseDto POJO
 * @author Diovana Valim
 * @version 0.0.1
 * @see java.lang.Object
 */
public class TotalProductByWarehouseDto {
    private long warehouseCode;
    private int totalQuantity;
}
