package dh.meli.projeto_integrador.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the Warehouse Class
 */
@Getter

/**
 * Method Setter implemented by Lombok lib for set access the private attributes of the Warehouse Class
 */
@Setter

/**
 * The @Entity annotation marks the Warehouse Class as an entity bean,
 * so it must have a no-argument constructor that is visible at least with a protected scope.
 */
@Entity

/**
 * The @Table annotation is used to specify table details that will be used to persist our entities in the database.
 */
@Table(name = "warehouse")

/**
 * Class created for modeling the Warehouse entity on the database
 * @author Diovana Valim
 * @version 0.0.1
 * @see java.lang.Object
 */
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnoreProperties("warehouse")
    private Set<Section> sections;

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnoreProperties("warehouse")
    private Set<Agent> agents;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;
}
