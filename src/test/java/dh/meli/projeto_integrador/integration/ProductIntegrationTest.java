package dh.meli.projeto_integrador.integration;

import dh.meli.projeto_integrador.model.Product;
import dh.meli.projeto_integrador.repository.IProductRepository;
import dh.meli.projeto_integrador.util.Generators;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
    }

    @Test
    public void findAllProducts_ReturnListOfAllProducts_whenFindAllSuccess() throws Exception {
        List<Product> list = new ArrayList<>();
        list.add(Generators.validProduct1());
        list.add(Generators.validProduct2());

        productRepository.saveAll(list);

        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/")
                .contentType(MediaType.APPLICATION_JSON));


        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(list.size())))
                .andExpect(jsonPath("$[0].name",
                        CoreMatchers.is(Generators.validProduct1().getName())));;
    }

    @Test
    public void findAllProducts_ReturnNotFound_whenProductsDoesntExists() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/")
                .contentType(MediaType.APPLICATION_JSON));


        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is("No Products Found")))
                .andExpect(jsonPath("$.status",
                        CoreMatchers.is(404)))
                .andExpect(jsonPath("$.title",
                        CoreMatchers.is("Object Not Found")));
    }

    @Test
    public void findProductsByCategory_ReturnListOfProducts_whenFindAllSuccess() throws Exception {
        List<Product> list = new ArrayList<>();
        list.add(Generators.validProduct1());
        list.add(Generators.validProduct2());

        productRepository.saveAll(list);

        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/{category}", Generators.validProduct2().getType())
                .contentType(MediaType.APPLICATION_JSON));


        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(list.size() -1)))
                .andExpect(jsonPath("$[0].name",
                        CoreMatchers.is(Generators.validProduct2().getName())))
                .andExpect(jsonPath("$[0].type",
                        CoreMatchers.is(Generators.validProduct2().getType())));
    }

    @Test
    public void findAll_ReturnEmptylist_whenProductsDoesntExists() throws Exception {
        Product newProduct1 = Generators.validProduct1();
        Product newProduct2 = Generators.validProduct2();

        productRepository.save(newProduct1);

        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/{category}", newProduct2.getType())
                        .contentType(MediaType.APPLICATION_JSON));


        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is("No Products Found")))
                .andExpect(jsonPath("$.status",
                        CoreMatchers.is(404)))
                .andExpect(jsonPath("$.title",
                        CoreMatchers.is("Object Not Found")));
    }
}
