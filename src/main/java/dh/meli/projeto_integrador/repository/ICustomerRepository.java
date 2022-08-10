package dh.meli.projeto_integrador.repository;

import dh.meli.projeto_integrador.model.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface ICustomerRepository will manage data persistence for Customer object instances.
 * Will read, save, update and delete data through the GET, POST, PUT and DELETE requests.
 * @author Gabriela Azevedo
 * @version 0.0.1
 */
public interface ICustomerRepository extends CrudRepository<Customer, Long> {
}
