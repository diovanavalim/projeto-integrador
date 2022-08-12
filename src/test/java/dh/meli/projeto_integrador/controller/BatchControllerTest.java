package dh.meli.projeto_integrador.controller;

import dh.meli.projeto_integrador.dto.dtoInput.CategoryDto;
import dh.meli.projeto_integrador.dto.dtoOutput.BatchStockDto;
import dh.meli.projeto_integrador.model.Batch;
import dh.meli.projeto_integrador.model.Product;
import dh.meli.projeto_integrador.service.BatchService;
import dh.meli.projeto_integrador.util.Generators;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BatchControllerTest {

    @InjectMocks
    BatchController batchController;

    @Mock
    BatchService batchService;

    @Test
    void getBatchBySectionOrderedByDueDateTest() {
        Batch batch = Generators.getBatch();
        Product product = Generators.getProduct();

        BDDMockito.when(batchService.getBatchBySectionOrderedByDueDate(ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyLong())).thenReturn(Generators.getBatchStockDto(batch, product));

        ResponseEntity<List<BatchStockDto>> response = batchController
                .getBatchBySectionOrderedByDueDate(batch.getOrderEntry().getSection().getId(), 90);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody().get(0).getBatchNumber()).isEqualTo(batch.getId());

        verify(batchService, atLeastOnce())
                .getBatchBySectionOrderedByDueDate(batch.getOrderEntry().getSection().getId(), 90);
    }

    @Test
    void getBatchByProductTypeOrderedByDueDate() {
        Batch batch = Generators.getBatch();
        Product product = Generators.getProduct();
        String category = "FS";
        CategoryDto categoryDto = new CategoryDto(category);

        BDDMockito.when(batchService.getBatchByProductTypeOrderedByDueDate(ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(CategoryDto.class))).thenReturn(Generators.getBatchStockDto(batch, product));

        ResponseEntity<List<BatchStockDto>> response = batchController
                .getBatchByProductTypeOrderedByDueDate(90, categoryDto.getCategory());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody().get(0).getBatchNumber()).isEqualTo(batch.getId());

        verify(batchService, atLeastOnce())
                .getBatchByProductTypeOrderedByDueDate(ArgumentMatchers.anyLong(),
                        ArgumentMatchers.any(CategoryDto.class));
    }
}
