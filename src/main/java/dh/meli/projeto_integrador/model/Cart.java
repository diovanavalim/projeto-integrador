package dh.meli.projeto_integrador.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dh.meli.projeto_integrador.enumClass.PurchaseOrderStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the Cart Class
 */
@Getter

/**
 * Method Setter implemented by Lombok lib for set access the private attributes of the Cart Class
 */
@Setter

/**
 * The @Entity annotation marks the Cart Class as an entity bean,
 * so it must have a no-argument constructor that is visible at least with a protected scope.
 */
@Entity

/**
 * The @Table annotation is used to specify table details that will be used to persist our entities in the database.
 */
@Table(name = "cart")

/**
 * Method Builder implemented by Lombok lib
 */
@Builder

/**
 * Class created for modeling the Cart entity on the database
 * @author Diovana Valim
 * @version 0.0.1
 * @see java.lang.Object
 */
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonIgnoreProperties("carts")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private PurchaseOrderStatusEnum status;

    private LocalDate date;

    @OneToMany(mappedBy = "cart")
    @JsonIgnoreProperties("cart")
    private Set<ProductCart> productCarts;
}
