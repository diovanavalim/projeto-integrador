package dh.meli.projeto_integrador.repository;

import dh.meli.projeto_integrador.model.Agent;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface IAgentRepository will manage data persistence for Agent object instances.
 * Will read, save, update and delete data through the GET, POST, PUT and DELETE requests.
 * @author Diovana Valim
 * @version 0.0.1
 */
public interface IAgentRepository extends CrudRepository<Agent, Long> {}
