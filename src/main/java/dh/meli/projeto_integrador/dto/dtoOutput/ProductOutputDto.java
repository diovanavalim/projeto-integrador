package dh.meli.projeto_integrador.dto.dtoOutput;

import dh.meli.projeto_integrador.model.Product;
import lombok.*;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the Product Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the Product Class
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
 * Class used to create a Data Transfer Object for Output Product POJO
 * @author Rafael Cavalcante
 * @version 0.0.1
 * @see java.lang.Object
 */
public class ProductOutputDto {
    private String name;
    private String type;
    private Double price;

    /**
     * Method for constructing an ProductDto from a Product
     * @param product an object of type Property
     */
    public ProductOutputDto(Product product){
        this.name = product.getName();
        this.type = product.getType();
        this.price = product.getPrice();
    }
}
