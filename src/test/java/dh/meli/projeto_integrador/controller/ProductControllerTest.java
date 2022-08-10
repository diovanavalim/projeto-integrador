package dh.meli.projeto_integrador.controller;

import dh.meli.projeto_integrador.dto.dtoOutput.ListProductByWarehouseDto;
import dh.meli.projeto_integrador.dto.dtoOutput.ProductOutputDto;
import dh.meli.projeto_integrador.service.ProductService;
import dh.meli.projeto_integrador.util.Generators;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.anyString;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @MockBean
    ProductService service;

    @Test
    void listAllProducts_returnListOfProducts_whenSuccessTest() throws Exception {
        List<ProductOutputDto> list = Generators.productDtoList();
        BDDMockito.when(service.getAllProducts())
                .thenReturn(list);

        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(list.size())))
                .andExpect(jsonPath("$[0].name",
                        CoreMatchers.is(Generators.validProductDto1().getName())));
    }


    @Test
    void listProductByCategoryTest() throws Exception {
        List<ProductOutputDto> list = Generators.productDtoList();
        BDDMockito.when(service.getProductsByCategory(anyString()))
                .thenReturn(list);

        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/{cayegory}",
                Generators.validProductDto1().getType())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(list.size())))
                .andExpect(jsonPath("$[0].name",
                        CoreMatchers.is(Generators.validProductDto1().getName())));
    }

    @Test
    void listProductByWarehouseTest() {
        BDDMockito.when(productService.listProductByWarehouse(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getListProductByWarehouseDto());

        long id = 1;

        ResponseEntity<ListProductByWarehouseDto> response = productController.listProductByWarehouse(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody().getProductId())
                .isEqualTo(Generators.getListProductByWarehouseDto().getProductId());
        assertThat(response.getBody().getWarehouses().get(0).getWarehouseCode())
                .isEqualTo(Generators.getListProductByWarehouseDto().getWarehouses().get(0).getWarehouseCode());

        verify(productService, atLeastOnce()).listProductByWarehouse(id);
    }
}