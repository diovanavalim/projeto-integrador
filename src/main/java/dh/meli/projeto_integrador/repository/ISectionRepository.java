package dh.meli.projeto_integrador.repository;

import dh.meli.projeto_integrador.model.Section;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface ISectionRepository will manage data persistence for Section object instances.
 * Will read, save, update and delete data through the GET, POST, PUT and DELETE requests.
 * @author Diovana Valim
 * @version 0.0.1
 */
public interface ISectionRepository extends CrudRepository<Section, Long> {}
