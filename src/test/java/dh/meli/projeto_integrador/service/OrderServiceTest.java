package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.BatchDto;
import dh.meli.projeto_integrador.dto.dtoInput.OrderEntryDto;
import dh.meli.projeto_integrador.exception.ForbiddenException;
import dh.meli.projeto_integrador.model.*;
import dh.meli.projeto_integrador.repository.IOrderRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    IOrderRepository orderRepository;

    @Mock
    ProductService productService;

    @Mock
    WarehouseService warehouseService;

    @Mock
    SectionService sectionService;

    @Mock
    AgentService agentService;

    @Mock
    BatchService batchService;

    @BeforeEach
    void setup() {
        BDDMockito.when(orderRepository.save(ArgumentMatchers.any(OrderEntry.class)))
                .thenReturn(Generators.getOrderEntry());
        BDDMockito.when(productService.findProduct(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getProduct());
        BDDMockito.when(warehouseService.findWarehouse(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getWarehouse());
        BDDMockito.when(sectionService.findSection(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getSection());
        BDDMockito.when(sectionService.saveSection(ArgumentMatchers.any(Section.class)))
                .thenReturn(Generators.getSection());
        BDDMockito.when(agentService.findAgent(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getAgent());
        BDDMockito.when(batchService.createBatch(ArgumentMatchers.any(Batch.class)))
                .thenReturn(Generators.createBatch());
    }

    @Test
    void createInboundOrderTest() {
        OrderEntryDto orderEntryDto = Generators.createOrderEntryDto();

        List<BatchDto> batches = orderService.createInboundOrder(orderEntryDto);

        OrderEntry generatedOrderEntry = Generators.getOrderEntry();
        ArrayList<Batch> generatedBatches = new ArrayList<Batch>(generatedOrderEntry.getBatches());

        assertThat(batches.size()).isEqualTo(1);
        assertThat(batches.get(0).getBatchId()).isEqualTo(generatedBatches.get(0).getId());
        assertThat(batches.get(0).getDueDate()).isEqualTo(generatedBatches.get(0).getDueDate());

        verify(warehouseService, atLeastOnce()).findWarehouse(orderEntryDto.getSection().getWarehouseId());
        verify(sectionService, atLeastOnce()).findSection(orderEntryDto.getSection().getSectionId());
        verify(agentService, atLeastOnce()).findAgent(orderEntryDto.getAgentId());
    }

    @Test
    void createInboundOrder_ExceedingMaxProductLoadTest() throws Exception {
        OrderEntryDto orderEntryDto = Generators.createOrderEntryDto();

        Set<BatchDto> batchDtoSet = orderEntryDto.getBatchStock();

        for (BatchDto batchDto : batchDtoSet) {
            batchDto.setInitialQuantity(10000);
        }

        orderEntryDto.setBatchStock(batchDtoSet);

        ForbiddenException exception = Assertions.assertThrows(ForbiddenException.class, () -> {
            List<BatchDto> batches = orderService.createInboundOrder(orderEntryDto);
        });

        assertThat(exception.getMessage()).isEqualTo(String.format("Product batches quantity sum overtakes section " +
                "maximum product load"));

        verify(warehouseService, atLeastOnce()).findWarehouse(orderEntryDto.getSection().getWarehouseId());
        verify(sectionService, atLeastOnce()).findSection(orderEntryDto.getSection().getSectionId());
        verify(agentService, atLeastOnce()).findAgent(orderEntryDto.getAgentId());
    }

    @Test
    void createInboundOrder_WrongProductSession() throws Exception {
        OrderEntryDto orderEntryDto = Generators.createOrderEntryDto();

        BDDMockito.when(sectionService.findSection(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getUnavailableSection());

        ForbiddenException exception = Assertions.assertThrows(ForbiddenException.class, () -> {
            List<BatchDto> batches = orderService.createInboundOrder(orderEntryDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Product's Maçã section does not equals the given section");

        verify(warehouseService, atLeastOnce()).findWarehouse(orderEntryDto.getSection().getWarehouseId());
        verify(sectionService, atLeastOnce()).findSection(orderEntryDto.getSection().getSectionId());
        verify(agentService, atLeastOnce()).findAgent(orderEntryDto.getAgentId());
    }

    @Test
    void createInboundOrder_WrongAgentWarehouseId() throws Exception {
        OrderEntryDto orderEntryDto = Generators.createOrderEntryDto();

        BDDMockito.when(agentService.findAgent(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getUnavailableAgent());

        ForbiddenException exception = Assertions.assertThrows(ForbiddenException.class, () -> {
            List<BatchDto> batches = orderService.createInboundOrder(orderEntryDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Agent's warehouse ID does not belong to section's warehouse ID");

        verify(warehouseService, atLeastOnce()).findWarehouse(orderEntryDto.getSection().getWarehouseId());
        verify(sectionService, atLeastOnce()).findSection(orderEntryDto.getSection().getSectionId());
        verify(agentService, atLeastOnce()).findAgent(orderEntryDto.getAgentId());
    }
}
