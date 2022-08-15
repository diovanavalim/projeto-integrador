package dh.meli.projeto_integrador.dto.dtoInput;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the CreateSectionDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the CreateSectionDto Class
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
 * Class used to create a Data Transfer Object for CreateSection POJO
 * @author Diovana Valim
 * @version 0.0.2
 * @see java.lang.Object
 */
public class CreateSectionDto {
    @NotNull(message = "You shall define a not null warehouseId parameter")
    @Min(1)
    private long warehouseId;

    @NotNull(message = "You shall define a not null productType parameter")
    @Pattern(regexp = "(^Refrigerado$)|(^Congelado$)|(^Fresco$)", message = "Parameter productType should be either " +
            "Refrigerado, Congelado or Fresco")
    private String productType;

    @NotNull(message = "You shall define a not null maxProductLoad parameter")
    @Min(1)
    private long maxProductLoad;
}
