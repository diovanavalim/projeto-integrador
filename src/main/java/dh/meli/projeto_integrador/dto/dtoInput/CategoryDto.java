package dh.meli.projeto_integrador.dto.dtoInput;

import dh.meli.projeto_integrador.exception.InvalidParameterTypeException;
import lombok.Getter;
import lombok.Setter;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the CategoryDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the CategoryDto Class
 */
@Setter
/**
 * Class used to create a Data Transfer Object for Category
 * @author Gabriela Azevedo
 * @version 0.0.1
 * @see java.lang.Object
 */
public class CategoryDto {
    private String category;

    private String productType;

    /**
     * Constructor that receives the String category to set the category and productType attributes
     * @param category String
     */
    public CategoryDto(String category) {
        this.category = category;

        switch (category) {
            case "FS":
                this.productType = "Fresco";
                break;
            case "RF":
                this.productType = "Refrigerado";
                break;
            case "FF":
                this.productType = "Congelado";
                break;
            default:
                throw new InvalidParameterTypeException(String.format("Invalid value %s for category path param",
                        category));
        }
    }
}
