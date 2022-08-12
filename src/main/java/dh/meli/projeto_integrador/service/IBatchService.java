package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.CategoryDto;
import dh.meli.projeto_integrador.dto.dtoOutput.BatchStockDto;
import dh.meli.projeto_integrador.model.Batch;
import dh.meli.projeto_integrador.model.OrderEntry;

import java.util.List;

/**
 * Interface to specify service methods implemented on BatchService class.
 * @author Diovana Valim
 * @version 0.0.1
 */
public interface IBatchService {

    /**
     * Method for to create a Batch
     * @param batch an object of type Batch coming from user request
     * @return an object of type Batch
     */
    Batch createBatch(Batch batch);

    /**
     * Method to find all batches that belongs to a given Order Entry;
     * @param orderEntry of type OrderEntry. OrderEntry instance;
     * @return a List of objects of type Batch;
     */
    List<Batch> findAllByOrderEntry(OrderEntry orderEntry);

    /**
     * Method used to get batch entries by section ordered by due date
     * @param sectionId of type long represents section identifier
     * @param numberOfDays of type long represents the amount of days further than now to query batchStock
     * @return a List of objects of type BatchStockDto
     */
    List<BatchStockDto> getBatchBySectionOrderedByDueDate(long sectionId, long numberOfDays);

    /**
     * Method used to get batch entries by category ordered by due date
     * @param category of type String represents the product type
     * @param numberOfDays of type long represents the amount of days further than now to query batchStock
     * @return a List of objects of type BatchStockDto
     */
    List<BatchStockDto> getBatchByProductTypeOrderedByDueDate(long numberOfDays, CategoryDto category);

    /**
     * Method used to filter batch entries by due date, ordering asc
     * @param batchList list of batch entries
     * @param numberOfDays of type long represents the amount of days further than now to query batchStock
     * @return a List of objects of type BatchStockDto
     */
    List<BatchStockDto> filterBatchStockByDueDate(long numberOfDays, List<Batch> batchList);
}
