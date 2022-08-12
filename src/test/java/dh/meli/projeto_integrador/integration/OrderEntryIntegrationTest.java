package dh.meli.projeto_integrador.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dh.meli.projeto_integrador.dto.dtoInput.BatchDto;
import dh.meli.projeto_integrador.dto.dtoInput.OrderEntryDto;
import dh.meli.projeto_integrador.model.*;
import dh.meli.projeto_integrador.repository.*;
import dh.meli.projeto_integrador.util.Generators;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderEntryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ISectionRepository sectionRepository;

    @Autowired
    private IWarehouseRepository warehouseRepository;

    @Autowired
    private IAgentRepository agentRepository;

    @Autowired
    private IBatchRepository batchRepository;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        batchRepository.deleteAll();
        orderRepository.deleteAll();
        sectionRepository.deleteAll();
        agentRepository.deleteAll();
        warehouseRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void createInboundOrderTest() throws Exception {
        Warehouse warehouse = warehouseRepository.save(Generators.getCleanWarehouse(1));

        Section section = sectionRepository.save(Generators.getCleanSection(warehouse, 2000));

        Product product = productRepository.save(Generators.getProduct());

        Agent agent = agentRepository.save(Generators.getCleanAgent(warehouse));

        OrderEntryDto orderEntryDto = Generators.getCleanOrderEntryDto(agent.getId(), warehouse.getId(),
                section.getId(), product.getId());

        ArrayList<BatchDto> batchDtoList = new ArrayList<BatchDto>(orderEntryDto.getBatchStock());

        BatchDto batchDto = batchDtoList.get(0);

        batchDto.setBatchId(1);

        int expectedBatchId = (int) batchDto.getBatchId();
        int expectedProductId = (int) batchDto.getProductId();
        double expectedCurrentTemperature = batchDto.getCurrentTemperature();

        ResultActions response = mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderEntryDto)));


        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].batchNumber",
                        CoreMatchers.is(expectedBatchId)))
                .andExpect(jsonPath("[0].productId",
                        CoreMatchers.is(expectedProductId)))
                .andExpect(jsonPath("[0].currentTemperature",
                        CoreMatchers.is(expectedCurrentTemperature)));
    }

    @Test
    void createInboundOrder_WhenAgentDoesNotBelongToWarehouseTest() throws Exception {
        Warehouse warehouse = warehouseRepository.save(Generators.getCleanWarehouse(0));

        Warehouse agentWarehouse = warehouseRepository.save(Generators.getCleanWarehouse(0));

        Section section = sectionRepository.save(Generators.getCleanSection(warehouse, 2000));

        Product product = productRepository.save(Generators.getProduct());

        Agent agent = agentRepository.save(Generators.getCleanAgent(agentWarehouse));

        OrderEntryDto orderEntryDto = Generators.getCleanOrderEntryDto(agent.getId(), warehouse.getId(),
                section.getId(), product.getId());

        ResultActions response = mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderEntryDto)));


        response.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is("Agent's warehouse ID does not belong to section's warehouse ID")))
                .andExpect(jsonPath("$.status",
                        CoreMatchers.is(403)))
                .andExpect(jsonPath("$.title",
                        CoreMatchers.is("Forbidden Operation")));
    }

    @Test
    void createInboundOrder_WhenSectionDoesNotBelongToWarehouseTest() throws Exception {
        Warehouse warehouse = warehouseRepository.save(Generators.getCleanWarehouse(0));

        Warehouse sectionWarehouse = warehouseRepository.save(Generators.getCleanWarehouse(0));

        Section section = sectionRepository.save(Generators.getCleanSection(sectionWarehouse, 2000));

        Product product = productRepository.save(Generators.getProduct());

        Agent agent = agentRepository.save(Generators.getCleanAgent(warehouse));

        OrderEntryDto orderEntryDto = Generators.getCleanOrderEntryDto(agent.getId(), warehouse.getId(),
                section.getId(), product.getId());

        ResultActions response = mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderEntryDto)));


        response.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is("Section does not belong to the given warehouse")))
                .andExpect(jsonPath("$.status",
                        CoreMatchers.is(403)))
                .andExpect(jsonPath("$.title",
                        CoreMatchers.is("Forbidden Operation")));
    }

    @Test
    void createInboundOrder_WhenProductSectionDoesNotMatchRequestSection() throws Exception {
        Warehouse warehouse = warehouseRepository.save(Generators.getCleanWarehouse(0));

        Section section = sectionRepository.save(Generators.getCleanSection(warehouse, 2000));

        Product product = productRepository.save(Generators.getCleanProduct());

        Agent agent = agentRepository.save(Generators.getCleanAgent(warehouse));

        OrderEntryDto orderEntryDto = Generators.getCleanOrderEntryDto(agent.getId(), warehouse.getId(),
                section.getId(), product.getId());

        ResultActions response = mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderEntryDto)));


        response.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is(String.format("Product's %s section does not equals the given section",
                                product.getName()))))
                .andExpect(jsonPath("$.status",
                        CoreMatchers.is(403)))
                .andExpect(jsonPath("$.title",
                        CoreMatchers.is("Forbidden Operation")));
    }

    @Test
    void createInboundOrder_WhenSectionHasNoAvailableStorage() throws Exception {
        Warehouse warehouse = warehouseRepository.save(Generators.getCleanWarehouse(0));

        Section section = sectionRepository.save(Generators.getCleanSection(warehouse, 130));

        Product product = productRepository.save(Generators.getProduct());

        Agent agent = agentRepository.save(Generators.getCleanAgent(warehouse));

        OrderEntryDto orderEntryDto = Generators.getCleanOrderEntryDto(agent.getId(), warehouse.getId(),
                section.getId(), product.getId());

        ResultActions response = mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderEntryDto)));


        response.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is("Product batches quantity sum overtakes section maximum product load")))
                .andExpect(jsonPath("$.status",
                        CoreMatchers.is(403)))
                .andExpect(jsonPath("$.title",
                        CoreMatchers.is("Forbidden Operation")));
    }
}
