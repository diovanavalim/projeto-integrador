package dh.meli.projeto_integrador.dto.dtoInput;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the WarehouseInputDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the WarehouseInputDto Class
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
 * Class used to create a Data Transfer Object for Warehouse Input POJO
 * @author Diovana Valim
 * @version 0.0.3
 * @see java.lang.Object
 */
public class WarehouseInputDto {
    @NotNull(message = "You shall define a valid name")
    private String name;

    @NotNull(message = "You shall define a valid address")
    private String address;
}