package dh.meli.projeto_integrador.dto.dtoOutput;

import dh.meli.projeto_integrador.model.Section;
import lombok.*;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the SectionDtoOutput Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the SectionDtoOutput Class
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
 * Class used to create a Data Transfer Object for Section POJO
 * @author Diovana Valim
 * @version 0.0.3
 * @see java.lang.Object
 */
public class SectionDtoOutput {
    private long id;

    private long warehouseId;

    private String productType;

    private long maxProductLoad;

    private long currentProductLoad;

    public SectionDtoOutput(Section section) {
        this.id = section.getId();
        this.warehouseId = section.getWarehouse().getId();
        this.productType = section.getProductType();
        this.maxProductLoad = section.getMaxProductLoad();
        this.currentProductLoad = section.getCurrentProductLoad();
    }
}
