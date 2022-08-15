package dh.meli.projeto_integrador.repository;

import dh.meli.projeto_integrador.model.Batch;
import org.springframework.data.jpa.repository.Query;
import dh.meli.projeto_integrador.model.OrderEntry;
import dh.meli.projeto_integrador.model.Product;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interface IBatchRepository will manage data persistence for Batch object instances.
 * Will read, save, update and delete data through the GET, POST, PUT and DELETE requests.
 * @author Diovana Valim, Thiago Almeida
 * @version 0.0.2
 */
public interface IBatchRepository extends CrudRepository<Batch, Long> {

    /**
     * Method that find a batch by product;
     * @param product an object of type Product;
     * @return an object of type Batch;
     */
    Batch findByProduct(Product product);

    /**

	 * Method for to find a Batch by product id
	 * @param id Long
	 * @return a list of objects of type Batch
	 */
	@Query(value = "SELECT * FROM batch WHERE product_id = ?1", nativeQuery = true)
    List<Batch> findBatchByProductId(long id);
    
	/**
	 * Method to find all batches that belongs to a given Order Entry;
	 * @param orderEntry of type OrderEntry. OrderEntry instance;
	 * @return a List of objects of type Batch;
	 */
	List<Batch> findByOrderEntry(OrderEntry orderEntry);

	/**
	 * Method to find all batches querying by section_id parameter;
	 * @param sectionId long that represents Section identifier;
	 * @return a List of objects of type Batch;
	 */
	@Query(value = "SELECT * from batch " +
			"JOIN order_entry ON order_entry.id = batch.id " +
			"JOIN section ON section.id = order_entry.section_id  " +
			"WHERE section.id = ?1", nativeQuery = true)
	List<Batch> findBatchBySectionId(long sectionId);

	/**
	 * Method to find all batches querying by associated product type;
	 * @param category String;
	 * @return a List of objects of type Batch;
	 */
	@Query(value = "SELECT * FROM batch JOIN product ON product.id = batch.product_id WHERE product.type = ?1",
			nativeQuery = true)
	List<Batch> findBatchByProductType(String category);

	/**
	 * Method to sum current_quantity in all batches querying by associated product Id;
	 * @param productId Long;
	 * @return a quantity type Interger;
	 */
	@Query(value = "SELECT SUM(current_quantity) FROM batch WHERE product_id=?1",
			nativeQuery = true)
    Integer findTotalQuantityByProductId(Long productId);
}
