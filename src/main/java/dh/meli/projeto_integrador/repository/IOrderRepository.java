package dh.meli.projeto_integrador.repository;

import dh.meli.projeto_integrador.model.OrderEntry;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface IOrderRepository will manage data persistence for OrderEntry object instances.
 * Will read, save, update and delete data through the GET, POST, PUT and DELETE requests.
 * @author Diovana Valim
 * @version 0.0.1
 */
public interface IOrderRepository extends CrudRepository<OrderEntry, Long> {}
