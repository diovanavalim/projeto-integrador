package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.CategoryDto;
import dh.meli.projeto_integrador.dto.dtoOutput.BatchStockDto;
import dh.meli.projeto_integrador.exception.InternalServerErrorException;
import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Batch;
import dh.meli.projeto_integrador.model.OrderEntry;
import dh.meli.projeto_integrador.repository.IBatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

/**
 * Class responsible for business rules and communication with the Batch Repository layer;
 * @author Diovana Valim, Thiago Almeida;
 * @version 0.0.2
 */
@Service
public class BatchService implements IBatchService {

    /**
     * Dependency Injection of the Agent Repository.
     */
    @Autowired
    private IBatchRepository batchRepository;

    /**
     * Method to save a new batch;
     * @param batch of type Batch. Batch instance;
     * @return an object of type Batch;
     */
    @Transactional
    @Override
    public Batch createBatch(Batch batch) {
        try {
            return batchRepository.save(batch);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Method to find all batches that belongs to a given Order Entry;
     * @param orderEntry of type OrderEntry. OrderEntry instance;
     * @return a List of objects of type Batch;
     */
    @Override
    public List<Batch> findAllByOrderEntry(OrderEntry orderEntry) {
        return batchRepository.findByOrderEntry(orderEntry);
    };

    /**
     * Method used to get batch entries by section ordered by due date
     * @param sectionId of type long represents section identifier
     * @param numberOfDays of type long represents the amount of days further than now to query batchStock
     * @return a List of objects of type BatchStockDto
     */
    @Override
    public List<BatchStockDto> getBatchBySectionOrderedByDueDate(long sectionId, long numberOfDays) {
        List<Batch> batchList =  batchRepository.findBatchBySectionId(sectionId);

        if (batchList.isEmpty()) {
            throw new ResourceNotFoundException("The given section does not have available batch stock");
        }

        return filterBatchStockByDueDate(numberOfDays, batchList);
    }

    /**
     * Method used to get batch entries by category ordered by due date
     * @param category of type String represents the product type
     * @param numberOfDays of type long represents the amount of days further than now to query batchStock
     * @return a List of objects of type BatchStockDto
     */
    @Override
    public List<BatchStockDto> getBatchByProductTypeOrderedByDueDate(long numberOfDays, CategoryDto category) {
        List<Batch> batchList = batchRepository.findBatchByProductType(category.getProductType());

        if (batchList.isEmpty()) {
            throw new ResourceNotFoundException("The given category does not have available batch stock");
        }

        return filterBatchStockByDueDate(numberOfDays, batchList);
    }

    /**
     * Method used to filter batch entries by due date, ordering asc
     * @param batchList list of batch entries
     * @param numberOfDays of type long represents the amount of days further than now to query batchStock
     * @return a List of objects of type BatchStockDto
     */
    @Override
    public List<BatchStockDto> filterBatchStockByDueDate(long numberOfDays, List<Batch> batchList) {
        LocalDate now = LocalDate.now();
        LocalDate finalDate = LocalDate.now().plusDays(numberOfDays);

        List<BatchStockDto> batchStockDtoList = new ArrayList<BatchStockDto>();

        for (Batch batch : batchList) {
            if (batch.getDueDate().isAfter(now) && batch.getDueDate().isBefore(finalDate)) {
                batchStockDtoList.add(new BatchStockDto(batch));
            }
        }

        batchStockDtoList.sort(Comparator.comparing(BatchStockDto::getDueDate));

        return batchStockDtoList;
    }
}

