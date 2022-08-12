package dh.meli.projeto_integrador.integration;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BatchIntegrationTest {

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
    void getBatchBySectionOrderedByDueDate() throws Exception {
        Warehouse warehouse = warehouseRepository.save(Generators.getCleanWarehouse(1));

        Section section = sectionRepository.save(Generators.getCleanSection(warehouse, 2000));

        Product product = productRepository.save(Generators.getProduct());

        OrderEntry orderEntry = orderRepository.save(Generators.getCleanOrderEntry(section));

        Batch batch = batchRepository.save(Generators.getCleanBatch(product, orderEntry));


        int expectedBatchId = (int) batch.getId();
        int expectedProductId = (int) batch.getProduct().getId();
        String expectedProductType = batch.getProduct().getType();

        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/due-date/section/{sectionId}/number-of-days/{numberOfDays}",
                        section.getId(), 90)
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].batchNumber",
                        CoreMatchers.is(expectedBatchId)))
                .andExpect(jsonPath("[0].productId",
                        CoreMatchers.is(expectedProductId)))
                .andExpect(jsonPath("[0].productType",
                        CoreMatchers.is(expectedProductType)));
    }

    @Test
    void getBatchBySectionOrderedByDueDate_WhenSectionHasNoBatches() throws Exception {
        Warehouse warehouse = warehouseRepository.save(Generators.getCleanWarehouse(1));

        Section section = sectionRepository.save(Generators.getCleanSection(warehouse, 2000));

        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/due-date/section/{sectionId}/number-of-days/{numberOfDays}",
                        section.getId(), 90)
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is("The given section does not have available batch stock")))
                .andExpect(jsonPath("$.status",
                        CoreMatchers.is(404)))
                .andExpect(jsonPath("$.title",
                        CoreMatchers.is("Resource Not Found")));
    }

    @Test
    void getBatchByProductTypeOrderedByDueDate() throws Exception {
        Warehouse warehouse = warehouseRepository.save(Generators.getCleanWarehouse(1));

        Section section = sectionRepository.save(Generators.getCleanSection(warehouse, 2000));

        Product product = productRepository.save(Generators.getProduct());

        OrderEntry orderEntry = orderRepository.save(Generators.getCleanOrderEntry(section));

        Batch batch = batchRepository.save(Generators.getCleanBatch(product, orderEntry));

        String category = "FS";

        int expectedBatchId = (int) batch.getId();
        int expectedProductId = (int) batch.getProduct().getId();
        String expectedProductType = batch.getProduct().getType();

        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/due-date/number-of-days/{numberOfDays}/category/{category}",
                        90, category)
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].batchNumber",
                        CoreMatchers.is(expectedBatchId)))
                .andExpect(jsonPath("[0].productId",
                        CoreMatchers.is(expectedProductId)))
                .andExpect(jsonPath("[0].productType",
                        CoreMatchers.is(expectedProductType)));
    }

    @Test
    void getBatchByProductTypeOrderedByDueDate_WhenSectionHasNoBatches() throws Exception {
        Warehouse warehouse = warehouseRepository.save(Generators.getCleanWarehouse(1));

        Section section = sectionRepository.save(Generators.getCleanSection(warehouse, 2000));

        String category = "FS";

        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/due-date/number-of-days/{numberOfDays}/category/{category}",
                        90, category)
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is("The given category does not have available batch stock")))
                .andExpect(jsonPath("$.status",
                        CoreMatchers.is(404)))
                .andExpect(jsonPath("$.title",
                        CoreMatchers.is("Resource Not Found")));
    }
}
