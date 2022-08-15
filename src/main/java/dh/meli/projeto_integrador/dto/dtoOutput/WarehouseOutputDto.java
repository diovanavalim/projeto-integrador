package dh.meli.projeto_integrador.dto.dtoOutput;

import dh.meli.projeto_integrador.model.Warehouse;
import lombok.*;

import java.util.List;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the WarehouseOutputDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the WarehouseOutputDto Class
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
 * Class used to create a Data Transfer Object for WarehouseOutput POJO
 * @author Diovana Valim
 * @version 0.0.2
 * @see java.lang.Object
 */
public class WarehouseOutputDto {
    private long id;

    private String name;

    private String address;

    private List<AgentDto> agents;

    private List<SectionDtoOutput> sections;

    public WarehouseOutputDto(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.name = warehouse.getName();
        this.address = warehouse.getAddress();
    }
}