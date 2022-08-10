package dh.meli.projeto_integrador.repository;

import dh.meli.projeto_integrador.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface IProductRepository will manage data persistence for Product object instances.
 * Will read, save, update and delete data through the GET, POST, PUT and DELETE requests.
 * @author Diovana Valim, Rafael Cavalcante
 * @version 0.0.1
 */
@Repository
public interface IProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAllByType(String type);
}
