package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.WarehouseInputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.AgentDto;
import dh.meli.projeto_integrador.dto.dtoOutput.SectionDtoOutput;
import dh.meli.projeto_integrador.exception.InternalServerErrorException;
import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Agent;
import dh.meli.projeto_integrador.model.Section;
import dh.meli.projeto_integrador.model.Warehouse;
import dh.meli.projeto_integrador.repository.IWarehouseRepository;
import dh.meli.projeto_integrador.util.Generators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

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

    @Test
    void createWarehouseTest() {
        BDDMockito.when(warehouseRepository.save(ArgumentMatchers.any(Warehouse.class)))
                .thenReturn(Generators.getWarehouse());

        WarehouseInputDto warehouseInputDto = new WarehouseInputDto(Generators.getWarehouse().getName(),
                Generators.getWarehouse().getAddress());

        Warehouse warehouse = warehouseService.createWarehouse(warehouseInputDto);

        assertThat(warehouse.getAddress()).isEqualTo(warehouseInputDto.getAddress());
        assertThat(warehouse.getName()).isEqualTo(warehouseInputDto.getName());
        assertThat(warehouse.getId()).isEqualTo(Generators.getWarehouse().getId());
    }

    @Test
    void createWarehouseTest_WhenDatabaseIsUnavailable() throws Exception {
        BDDMockito.when(warehouseRepository.save(ArgumentMatchers.any(Warehouse.class)))
                .thenThrow(InternalServerErrorException.class);

        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
            Warehouse warehouse = warehouseService.createWarehouse(Generators.getWarehouseInputDto());
        });

        verify(warehouseRepository, atLeastOnce()).save(ArgumentMatchers.any(Warehouse.class));
    }

    @Test
    void findWarehouseTest() {
        BDDMockito.when(warehouseRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(Generators.getWarehouse()));

        long id = 0;
        Warehouse warehouse = warehouseService.findWarehouse(id);

        assertThat(warehouse.getId()).isEqualTo(Generators.getWarehouse().getId());
        assertThat(warehouse.getAddress()).isEqualTo(Generators.getWarehouse().getAddress());
        assertThat(warehouse.getName()).isEqualTo(Generators.getWarehouse().getName());

        verify(warehouseRepository, atLeastOnce()).findById(id);
    }

    @Test
    void findWarehouseTest_WhenDatabaseIsUnavailable() throws Exception {
        BDDMockito.when(warehouseRepository.findById(ArgumentMatchers.anyLong()))
                .thenThrow(InternalServerErrorException.class);

        long id = 1;

        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
            Warehouse warehouseList = warehouseService.findWarehouse(id);
        });

        verify(warehouseRepository, atLeastOnce()).findById(ArgumentMatchers.anyLong());
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

    @Test
    void findWarehousesTest() {
        List<Warehouse> warehouseList = new ArrayList<Warehouse>();

        warehouseList.add(Generators.getWarehouse());

        BDDMockito.when(warehouseRepository.findAll()).thenReturn(warehouseList);

        List<Warehouse> response = warehouseService.findWarehouses();

        assertThat(response.get(0).getName()).isEqualTo(Generators.getWarehouse().getName());
        assertThat(response.get(0).getAddress()).isEqualTo(Generators.getWarehouse().getAddress());
        assertThat(response.get(0).getId()).isEqualTo(Generators.getWarehouse().getId());

        verify(warehouseRepository, atLeastOnce()).findAll();
    }

    @Test
    void findWarehousesTest_WhenDatabaseIsUnavailable() throws Exception {
        BDDMockito.when(warehouseRepository.findAll()).thenThrow(InternalServerErrorException.class);

        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
            List<Warehouse> warehouseList = warehouseService.findWarehouses();
        });

        verify(warehouseRepository, atLeastOnce()).findAll();
    }

    @Test
    void setWarehouseSectionsTest() {
        List<SectionDtoOutput> sectionDtoOutputList = warehouseService.setWarehouseSections(Generators.getWarehouse());

        ArrayList<Section> sectionList = new ArrayList<Section>(Generators.getWarehouse().getSections());

        assertThat(sectionDtoOutputList.get(0).getProductType()).isEqualTo(sectionList.get(0).getProductType());
        assertThat(sectionDtoOutputList.get(0).getMaxProductLoad()).isEqualTo(sectionList.get(0).getMaxProductLoad());
        assertThat(sectionDtoOutputList.get(0).getWarehouseId()).isEqualTo(sectionList.get(0).getWarehouse().getId());
    }

    @Test
    void setWarehouseAgentsTest() {
        Agent agent = Generators.getAgent();

        Set<Agent> agentSet = new HashSet<Agent>();

        agentSet.add(agent);

        Warehouse warehouse = Generators.getWarehouse();

        warehouse.setAgents(agentSet);

        List<AgentDto> agentDtoList = warehouseService.setWarehouseAgents(warehouse);

        assertThat(agentDtoList.get(0).getName()).isEqualTo(agent.getName());
        assertThat(agentDtoList.get(0).getEmailAddress()).isEqualTo(agent.getEmailAddress());
        assertThat(agentDtoList.get(0).getPhoneNumber()).isEqualTo(agent.getPhoneNumber());
    }
}
