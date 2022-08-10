package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Warehouse;
import dh.meli.projeto_integrador.repository.IWarehouseRepository;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WarehouseServiceTest {

    @InjectMocks
    WarehouseService warehouseService;

    @Mock
    IWarehouseRepository warehouseRepository;

    @BeforeEach
    void setup() {
        BDDMockito.when(warehouseRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(Generators.getWarehouse()));
    }

    @Test
    void findWarehouseTest() {
        long id = 0;
        Warehouse warehouse = warehouseService.findWarehouse(id);

        assertThat(warehouse.getId()).isEqualTo(Generators.getWarehouse().getId());
        assertThat(warehouse.getAddress()).isEqualTo(Generators.getWarehouse().getAddress());
        assertThat(warehouse.getName()).isEqualTo(Generators.getWarehouse().getName());

        verify(warehouseRepository, atLeastOnce()).findById(id);
    }

    @Test
    void findWarehouse_WhenWarehouseDontExistsTest() throws Exception {
        BDDMockito.when(warehouseRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        long id = 0;

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Warehouse warehouse = warehouseService.findWarehouse(id);
        });

        assertThat(exception.getMessage()).isEqualTo(String.format("Could not find valid warehouse for id %d", id));

        verify(warehouseRepository, atLeastOnce()).findById(id);
    }
}
