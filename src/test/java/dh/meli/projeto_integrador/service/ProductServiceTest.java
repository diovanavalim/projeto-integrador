package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoOutput.ListProductByWarehouseDto;
import dh.meli.projeto_integrador.dto.dtoOutput.ProductOutputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.TotalProductByWarehouseDto;
import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Batch;
import dh.meli.projeto_integrador.model.Product;
import dh.meli.projeto_integrador.repository.IBatchRepository;
import dh.meli.projeto_integrador.repository.IProductRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    IProductRepository productRepository;

    @Mock
    IBatchRepository batchRepository;

    @BeforeEach
    void setup() {
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(Generators.getProduct()));
    }

    @Test
    void findProductTest() {
        long id = 0;
        Product product = productService.findProduct(id);

        assertThat(product.getId()).isEqualTo(Generators.getProduct().getId());
        assertThat(product.getType()).isEqualTo(Generators.getProduct().getType());
        assertThat(product.getPrice()).isEqualTo(Generators.getProduct().getPrice());

        verify(productRepository, atLeastOnce()).findById(id);
    }

    @Test
    void findProduct_WhenProductDontExistsTest() throws Exception {
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        long id = 0;

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Product product = productService.findProduct(id);
        });

        assertThat(exception.getMessage()).isEqualTo(String.format("Could not find valid product for id %d", id));

        verify(productRepository, atLeastOnce()).findById(id);
    }

    @Test
    void getAllProducts_returnListProducts_whenProductsExists() {
        BDDMockito.when(productRepository.findAll())
                .thenReturn(Generators.productList());

        List<ProductOutputDto> products = productService.getAllProducts();


        assertThat(products).isNotNull();
        assertThat((products.size())).isEqualTo(2);
    }

    @Test
    void getAllProducts_returnListProducts_whenProductsDontExist() {
        BDDMockito.when(productRepository.findAll())
                .thenReturn(Generators.emptyProductDtoList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getAllProducts();
        });

        assertThat(exception.getMessage()).isEqualTo("No Products Found");
    }

    @Test
    void getProductsByCategory_returnListProducts_whenProductsExists() {
        BDDMockito.when(productRepository.findAllByType(anyString()))
                .thenReturn(Generators.productList());

        List<ProductOutputDto> products = productService.getProductsByCategory(Generators.validProduct1().getType());


        assertThat(products).isNotNull();
        assertThat((products.size())).isEqualTo(2);
    }

    @Test
    void getProductsByCategory_returnListProducts_whenProductsDontExist() {
        BDDMockito.when(productRepository.findAllByType(anyString()))
                .thenReturn(Generators.emptyProductDtoList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductsByCategory(Generators.validProduct1().getType());
        });

        assertThat(exception.getMessage()).isEqualTo("No Products Found");
    }

    @Test
    void listProductByWarehouseTest() {
        BDDMockito.when(batchRepository.findBatchByProductId(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getBatches());


        Product product = Generators.getProduct();

        ListProductByWarehouseDto listProductByWarehouseDto = productService.listProductByWarehouse(product.getId());

        assertThat(listProductByWarehouseDto.getProductId())
                .isEqualTo(Generators.getListProductByWarehouseDto().getProductId());

        for (int i = 0; i < listProductByWarehouseDto.getWarehouses().size(); i++) {
            TotalProductByWarehouseDto totalProductByWarehouseDto = listProductByWarehouseDto.getWarehouses().get(i);

            TotalProductByWarehouseDto generatedTotalProductByWarehouseDto = Generators
                    .getListProductByWarehouseDto()
                    .getWarehouses()
                    .get(i);

            assertThat(totalProductByWarehouseDto.getWarehouseCode())
                    .isEqualTo(generatedTotalProductByWarehouseDto.getWarehouseCode());
            assertThat(totalProductByWarehouseDto.getTotalQuantity())
                    .isEqualTo(generatedTotalProductByWarehouseDto.getTotalQuantity());
        }

        verify(batchRepository, atLeastOnce()).findBatchByProductId(product.getId());
    }

    @Test
    void listProductByWarehouse_WhenBatchListIsEmptyTest() throws Exception {
        List<Batch> batchList = new ArrayList<Batch>();
        BDDMockito.when(batchRepository.findBatchByProductId(ArgumentMatchers.anyLong()))
                .thenReturn(batchList);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            ListProductByWarehouseDto listProductByWarehouseDto = productService.listProductByWarehouse(
                    Generators.getProduct().getId()
            );
        });

        assertThat(exception.getMessage()).isEqualTo(String.format("Could not find valid batch stock for product %d",
                Generators.getProduct().getId()));

        verify(batchRepository, atLeastOnce()).findBatchByProductId(Generators.getProduct().getId());
    }
}
