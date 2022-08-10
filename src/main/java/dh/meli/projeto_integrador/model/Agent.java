package dh.meli.projeto_integrador.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the Agent Class
 */
@Getter

/**
 * Method Setter implemented by Lombok lib for set the private attributes of the Agent Class
 */
@Setter

/**
 * The @Entity annotation marks the Agent Class as an entity bean,
 * so it must have a no-argument constructor that is visible at least with a protected scope.
 */
@Entity

/**
 * The @Table annotation is used to specify table details that will be used to persist our entities in the database.
 */
@Table(name = "agent")
/**
 * Class created for modeling the Agent entity on the database
 * @author Diovana Valim
 * @version 0.0.1
 * @see java.lang.Object
 */
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    @JsonIgnoreProperties("agents")
    private Warehouse warehouse;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email_address")
    private String emailAddress;
}
