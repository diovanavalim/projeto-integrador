package dh.meli.projeto_integrador.controller;

import dh.meli.projeto_integrador.dto.dtoInput.CategoryDto;
import dh.meli.projeto_integrador.dto.dtoOutput.BatchStockDto;
import dh.meli.projeto_integrador.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class responsible for processing user's requests and generating appropriated HTTP responses;
 * @author Diovana Valim
 * @version 0.0.1;
 */
@RestController
@RequestMapping("/api/v1")
public class BatchController {

    /**
     * Dependency Injection of the Batch Service.
     */
    @Autowired
    private BatchService batchService;

    /**
     * A get method responsible for find all batch entries by section, ordered by due date asc
     * @param sectionId long that represents Section entity identifier
     * @param numberOfDays of type long represents the amount of days further than now to query batchStock
     * @return Response Entity with a List of BatchStockDto and the corresponding HttpStatus;
     */
    @GetMapping("/fresh-products/due-date/section/{sectionId}/number-of-days/{numberOfDays}")
    public ResponseEntity<List<BatchStockDto>> getBatchBySectionOrderedByDueDate(@PathVariable long sectionId,
                                                                    @PathVariable long numberOfDays) {
        List<BatchStockDto> batchStockDtoList = batchService.getBatchBySectionOrderedByDueDate(sectionId,
                numberOfDays);

        return new ResponseEntity<List<BatchStockDto>>(batchStockDtoList, HttpStatus.OK);
    }

    /**
     * A get method responsible for find all batch entries by product type, ordered by due date asc
     * @param category String code that represents a product type attribute
     * @param numberOfDays of type long represents the amount of days further than now to query batchStock
     * @return Response Entity with a List of BatchStockDto and the corresponding HttpStatus;
     */
    @GetMapping("/fresh-products/due-date/number-of-days/{numberOfDays}/category/{category}")
    public ResponseEntity<List<BatchStockDto>> getBatchByProductTypeOrderedByDueDate(@PathVariable long numberOfDays,
                                                                                  @PathVariable String category) {
        List<BatchStockDto> batchStockDtoList = batchService.getBatchByProductTypeOrderedByDueDate(numberOfDays,
                new CategoryDto(category));

        return new ResponseEntity<List<BatchStockDto>>(batchStockDtoList, HttpStatus.OK);
    }
}
