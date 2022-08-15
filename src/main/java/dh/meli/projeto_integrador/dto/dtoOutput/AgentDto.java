package dh.meli.projeto_integrador.dto.dtoOutput;

import dh.meli.projeto_integrador.model.Agent;
import lombok.*;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the AgentDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the AgentDto Class
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
 * Class used to create a Data Transfer Object for Agent POJO
 * @author Diovana Valim
 * @version 0.0.2
 * @see java.lang.Object
 */
public class AgentDto {
    private long id;

    private String name;

    private String phoneNumber;

    private String emailAddress;

    public AgentDto(Agent agent) {
        this.id = agent.getId();
        this.name = agent.getName();
        this.phoneNumber = agent.getPhoneNumber();
        this.emailAddress = agent.getEmailAddress();
    }
}