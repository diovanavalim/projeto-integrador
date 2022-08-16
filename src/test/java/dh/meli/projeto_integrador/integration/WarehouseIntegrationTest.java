package dh.meli.projeto_integrador.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dh.meli.projeto_integrador.dto.dtoInput.WarehouseInputDto;
import dh.meli.projeto_integrador.model.Warehouse;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WarehouseIntegrationTest {

    @Autowired
    MockMvc mockMvc;

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
    void createWarehouseTest() throws Exception {
        WarehouseInputDto warehouseInputDto = new WarehouseInputDto("Warehouse Cajamar",
                "Av. das Nações Unidas, 3003");

        ResultActions response = mockMvc.perform(
                post("/api/v1/warehouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(warehouseInputDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(5)))
                .andExpect(jsonPath("$.id",
                        CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name",
                        CoreMatchers.is(warehouseInputDto.getName())))
                .andExpect(jsonPath("$.address",
                        CoreMatchers.is(warehouseInputDto.getAddress())));
    }

    @Test
    void findWarehouseTest() throws Exception {
        Warehouse warehouse = warehouseRepository.save(Generators.getCleanWarehouse(0));

        ResultActions response = mockMvc.perform(
                get("/api/v1/warehouse/{id}", warehouse.getId())
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(5)))
                .andExpect(jsonPath("$.id",
                        CoreMatchers.is((int) Generators.getCleanWarehouse(warehouse.getId()).getId())))
                .andExpect(jsonPath("$.name",
                        CoreMatchers.is(Generators.getCleanWarehouse(warehouse.getId()).getName())))
                .andExpect(jsonPath("$.address",
                        CoreMatchers.is(Generators.getCleanWarehouse(warehouse.getId()).getAddress())));
    }

    @Test
    void findWarehouseTest_WhenWarehouseDoesNotExist() throws Exception {
        long id = 100;

        ResultActions response = mockMvc.perform(
                get("/api/v1/warehouse/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is(String.format(String.format("Could not find valid warehouse for id %d", id)))))
                .andExpect(jsonPath("$.status",
                        CoreMatchers.is(404)))
                .andExpect(jsonPath("$.title",
                        CoreMatchers.is("Resource Not Found")));
    }

    @Test
    void findWarehousesTest() throws Exception {
        Warehouse warehouse = warehouseRepository.save(Generators.getCleanWarehouse(0));

        ResultActions response = mockMvc.perform(
                get("/api/v1/warehouse")
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].id",
                        CoreMatchers.is((int) Generators.getCleanWarehouse(warehouse.getId()).getId())))
                .andExpect(jsonPath("$[0].name",
                        CoreMatchers.is(Generators.getCleanWarehouse(warehouse.getId()).getName())))
                .andExpect(jsonPath("$[0].address",
                        CoreMatchers.is(Generators.getCleanWarehouse(warehouse.getId()).getAddress())));
    }
}
