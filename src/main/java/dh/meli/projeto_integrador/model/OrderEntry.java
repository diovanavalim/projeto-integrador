package dh.meli.projeto_integrador.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the OrderEntry Class
 */
@Getter

/**
 * Method Setter implemented by Lombok lib for set access the private attributes of the OrderEntry Class
 */
@Setter

/**
 * The @Entity annotation marks the OrderEntry Class as an entity bean,
 * so it must have a no-argument constructor that is visible at least with a protected scope.
 */
@Entity

/**
 * The @Table annotation is used to specify table details that will be used to persist our entities in the database.
 */
@Table(name = "order_entry")

/**
 * Class created for modeling the OrderEntry entity on the database
 * @author Diovana Valim
 * @version 0.0.1
 * @see java.lang.Object
 */
public class OrderEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "orderEntry")
    @JsonIgnoreProperties("orderEntry")
    private Set<Batch> batches;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    @JsonIgnoreProperties("orderEntries")
    private Section section;
}
