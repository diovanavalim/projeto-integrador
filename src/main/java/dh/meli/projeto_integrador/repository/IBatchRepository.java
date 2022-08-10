package dh.meli.projeto_integrador.repository;

import dh.meli.projeto_integrador.model.Batch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interface IBatchRepository will manage data persistence for Batch object instances.
 * Will read, save, update and delete data through the GET, POST, PUT and DELETE requests.
 * @author Diovana Valim
 * @version 0.0.1
 */
public interface IBatchRepository extends CrudRepository<Batch, Long> {
    @Query(value = "SELECT * FROM batch WHERE product_id = ?1", nativeQuery = true)
    List<Batch> findBatchByProductId(long id);
}
