package dh.meli.projeto_integrador.controller;

import dh.meli.projeto_integrador.dto.dtoInput.WarehouseInputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.WarehouseOutputDto;
import dh.meli.projeto_integrador.model.Warehouse;
import dh.meli.projeto_integrador.service.WarehouseService;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WarehouseControllerTest {

    @InjectMocks
    WarehouseController warehouseController;

    @Mock
    WarehouseService warehouseService;

    @Test
    void createWarehouseTest() {
        BDDMockito.when(warehouseService.createWarehouse(ArgumentMatchers.any(WarehouseInputDto.class)))
                .thenReturn(Generators.getWarehouse());

        WarehouseInputDto warehouseInputDto = Generators.getWarehouseInputDto();

        ResponseEntity<WarehouseOutputDto> response = warehouseController.createWarehouse(warehouseInputDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(warehouseInputDto.getName());
        assertThat(response.getBody().getAddress()).isEqualTo(warehouseInputDto.getAddress());

        verify(warehouseService, atLeastOnce()).createWarehouse(ArgumentMatchers.any(WarehouseInputDto.class));
    }

    @Test
    void getWarehouseTest() {
        BDDMockito.when(warehouseService.findWarehouse(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getWarehouse());
        BDDMockito.when(warehouseService.setWarehouseSections(ArgumentMatchers.any(Warehouse.class)))
                .thenReturn(Generators.getSectionDtoOutputList());
        BDDMockito.when(warehouseService.setWarehouseAgents(ArgumentMatchers.any(Warehouse.class)))
                .thenReturn(Generators.getAgentDtoOutputList());

        ResponseEntity<WarehouseOutputDto> response = warehouseController.getWarehouse(
                Generators.getWarehouse().getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(Generators.getWarehouse().getName());
        assertThat(response.getBody().getAddress()).isEqualTo(Generators.getWarehouse().getAddress());

        verify(warehouseService, atLeastOnce()).findWarehouse(ArgumentMatchers.anyLong());
        verify(warehouseService, atLeastOnce()).setWarehouseAgents(ArgumentMatchers.any(Warehouse.class));
        verify(warehouseService, atLeastOnce()).setWarehouseSections(ArgumentMatchers.any(Warehouse.class));
    }

    @Test
    void getWarehousesTest() {
        List<Warehouse> warehouseList = new ArrayList<Warehouse>();

        warehouseList.add(Generators.getWarehouse());

        BDDMockito.when(warehouseService.findWarehouses())
                .thenReturn(warehouseList);
        BDDMockito.when(warehouseService.setWarehouseSections(ArgumentMatchers.any(Warehouse.class)))
                .thenReturn(Generators.getSectionDtoOutputList());
        BDDMockito.when(warehouseService.setWarehouseAgents(ArgumentMatchers.any(Warehouse.class)))
                .thenReturn(Generators.getAgentDtoOutputList());

        ResponseEntity<List<WarehouseOutputDto>> response = warehouseController.getWarehouses();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get(0).getAddress()).isEqualTo(Generators.getWarehouse().getAddress());
        assertThat(response.getBody().get(0).getName()).isEqualTo(Generators.getWarehouse().getName());
        assertThat(response.getBody().get(0).getId()).isEqualTo(Generators.getWarehouse().getId());

        verify(warehouseService, atLeastOnce()).findWarehouses();
        verify(warehouseService, atLeastOnce()).setWarehouseAgents(ArgumentMatchers.any(Warehouse.class));
        verify(warehouseService, atLeastOnce()).setWarehouseSections(ArgumentMatchers.any(Warehouse.class));
    }
}
