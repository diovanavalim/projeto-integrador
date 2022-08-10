package dh.meli.projeto_integrador.dto.dtoInput;

import dh.meli.projeto_integrador.enumClass.PurchaseOrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the CartDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the CartDto Class
 */
@Setter
/**
 * Method Constructor with all arguments implemented by Lombok lib
 */
@AllArgsConstructor
/**
 * Class used to create a Data Transfer Object for OrderEntry POJO
 * @author Gabriela Azevedo
 * @version 0.0.1
 * @see java.lang.Object
 */
public class CartDto {

    @NotNull(message = "The date of the creation cart has to be a date. Format: (yyyy-mm-dd)")
    private LocalDate date;

    @NotNull(message = "The buyer id cannot be null")
    private Long buyerId;

    @NotNull(message = "The status of the cart cannot be null")
    private PurchaseOrderStatusEnum orderStatus;

    @Size(min = 1, message = "The products list not to be empty")
    private List<@Valid ProductDto> products;
}
