package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.CategoryDto;
import dh.meli.projeto_integrador.dto.dtoOutput.BatchStockDto;
import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Batch;
import dh.meli.projeto_integrador.model.Product;
import dh.meli.projeto_integrador.repository.IBatchRepository;
import dh.meli.projeto_integrador.util.Generators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BatchServiceTest {

    @InjectMocks
    BatchService batchService;

    @Mock
    IBatchRepository batchRepository;

    @BeforeEach
    void setup() {
        BDDMockito.when(batchRepository.save(ArgumentMatchers.any(Batch.class)))
                .thenReturn(Generators.getBatch());
    }

    @Test
    void createBatchTest() {
        Batch batch = Generators.createBatch();
        Batch responseBatch = batchService.createBatch(batch);

        assertThat(responseBatch.getId()).isEqualTo(batch.getId());
        assertThat(responseBatch.getProduct().getId()).isEqualTo(batch.getProduct().getId());
        assertThat(responseBatch.getOrderEntry().getId()).isEqualTo(batch.getOrderEntry().getId());

        verify(batchRepository, atLeastOnce()).save(batch);
    }

    @Test
    void getBatchBySectionOrderedByDueDateTest() {
        Batch batch = Generators.getBatch();
        Product product = Generators.getProduct();
        List<BatchStockDto> generatedBatchStockDtoList = Generators.getBatchStockDto(batch, product);

        BDDMockito.when(batchRepository.findBatchBySectionId(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getBatches());

        List<BatchStockDto> batchStockDtoList = batchService
                .getBatchBySectionOrderedByDueDate(batch.getOrderEntry().getSection().getId(), 90);

        assertThat(batchStockDtoList.get(0).getBatchNumber())
                .isEqualTo(generatedBatchStockDtoList.get(0).getBatchNumber());
        assertThat(batchStockDtoList.get(0).getDueDate())
                .isEqualTo(generatedBatchStockDtoList.get(0).getDueDate());
        assertThat(batchStockDtoList.get(0).getProductId())
                .isEqualTo(generatedBatchStockDtoList.get(0).getProductId());

        verify(batchRepository, atLeastOnce()).findBatchBySectionId(ArgumentMatchers.anyLong());
    }

    @Test
    void getBatchBySectionOrderedByDueDateTest_WhenBatchListIsEmpty() throws Exception {
        List<Batch> batchList = new ArrayList<Batch>();

        Batch batch = Generators.getBatch();

        BDDMockito.when(batchRepository.findBatchBySectionId(ArgumentMatchers.anyLong()))
                .thenReturn(batchList);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            List<BatchStockDto> batchStockDtoList = batchService.getBatchBySectionOrderedByDueDate(
                    batch.getOrderEntry().getSection().getId(), 90);
        });

        assertThat(exception.getMessage()).isEqualTo("The given section does not have available batch stock");
    }

    @Test
    void getBatchByProductTypeOrderedByDueDate() {
        Batch batch = Generators.getBatch();
        Product product = Generators.getProduct();
        List<BatchStockDto> generatedBatchStockDtoList = Generators.getBatchStockDto(batch, product);

        String category = "FS";
        CategoryDto categoryDto = new CategoryDto(category);

        BDDMockito.when(batchRepository.findBatchByProductType(ArgumentMatchers.anyString()))
                .thenReturn(Generators.getBatches());

        List<BatchStockDto> batchStockDtoList = batchService
                .getBatchByProductTypeOrderedByDueDate(90, categoryDto);

        assertThat(batchStockDtoList.get(0).getBatchNumber())
                .isEqualTo(generatedBatchStockDtoList.get(0).getBatchNumber());
        assertThat(batchStockDtoList.get(0).getDueDate())
                .isEqualTo(generatedBatchStockDtoList.get(0).getDueDate());
        assertThat(batchStockDtoList.get(0).getProductId())
                .isEqualTo(generatedBatchStockDtoList.get(0).getProductId());

        verify(batchRepository, atLeastOnce()).findBatchByProductType(ArgumentMatchers.anyString());
    }

    @Test
    void getBatchByProductTypeOrderedByDueDate_WhenBatchListIsEmpty() throws Exception {
        List<Batch> batchList = new ArrayList<Batch>();

        String category = "FS";
        CategoryDto categoryDto = new CategoryDto(category);

        BDDMockito.when(batchRepository.findBatchByProductType(ArgumentMatchers.anyString()))
                .thenReturn(batchList);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            List<BatchStockDto> batchStockDtoList = batchService.getBatchByProductTypeOrderedByDueDate(
                    90, categoryDto);
        });

        assertThat(exception.getMessage()).isEqualTo("The given category does not have available batch stock");
    }

    @Test
    void filterBatchStockByDueDate() {
        List<Batch> batchList = Generators.getBatches();

        List<BatchStockDto> batchStockDtoList = batchService.filterBatchStockByDueDate(90, batchList);

        assertThat(batchStockDtoList.get(0).getBatchNumber()).isEqualTo(batchList.get(0).getId());
        assertThat(batchStockDtoList.get(0).getProductId()).isEqualTo(batchList.get(0).getProduct().getId());
        assertThat(batchStockDtoList.size()).isEqualTo(batchList.size());
    }

    @Test
    void filterBatchStockByDueDate_WhenNoBatchIsInFilter() {
        List<Batch> batchList = Generators.getBatches();

        List<BatchStockDto> batchStockDtoList = batchService.filterBatchStockByDueDate(20, batchList);

        assertThat(batchStockDtoList.size()).isEqualTo(0);
    }
}
