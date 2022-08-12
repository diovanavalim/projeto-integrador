package dh.meli.projeto_integrador.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the BatchCart Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set access the private attributes of the BatchCart Class
 */
@Setter
/**
 * The @Entity annotation marks the BatchCart Class as an entity bean,
 * so it must have a no-argument constructor that is visible at least with a protected scope.
 */
@Entity
/**
 * The @Table annotation is used to specify table details that will be used to persist our entities in the database.
 */
@Table(name = "product_cart")
/**
 * Method Builder implemented by Lombok lib
 */
@Builder
/**
 * Method Constructor with all arguments implemented by Lombok lib
 */
@AllArgsConstructor
/**
 * Method Constructor with no arguments implemented by Lombok lib
 */
@NoArgsConstructor
/**
 * Class created for modeling the BatchCart entity on the database
 * @author Diovana Valim
 * @version 0.0.1
 * @see java.lang.Object
 */
public class ProductCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

}
