package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.model.Batch;
import dh.meli.projeto_integrador.repository.IBatchRepository;
import dh.meli.projeto_integrador.util.Generators;
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
}
