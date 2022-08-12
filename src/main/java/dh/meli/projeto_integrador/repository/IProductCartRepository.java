package dh.meli.projeto_integrador.repository;

import dh.meli.projeto_integrador.model.ProductCart;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface IProductCartRepository will manage data persistence for ProductCart object instances.
 * Will read, save, update and delete data through the GET, POST, PUT and DELETE requests.
 * @author Gabriela Azevedo
 * @version 0.0.1
 */
public interface IProductCartRepository extends CrudRepository<ProductCart, Long> {
}
