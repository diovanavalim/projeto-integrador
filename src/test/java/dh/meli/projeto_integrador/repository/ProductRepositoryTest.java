package dh.meli.projeto_integrador.repository;

import dh.meli.projeto_integrador.model.Product;
import dh.meli.projeto_integrador.util.Generators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private IProductRepository productRepository;


    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
    }


    @Test
    public void findAllProducts_ReturnListOfAllProducts_whenFindAllSuccess() {
        Product newPerson1 = Generators.validProduct1();
        Product newPerson2 = Generators.validProduct2();
        productRepository.save(newPerson1);
        productRepository.save(newPerson2);

        List<Product> products = (List<Product>) productRepository.findAll();

        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(2);
    }
    @Test
    public void findAllProducts_ReturnEmptylist_whenProductsDoesntExists() {
        List<Product> products = (List<Product>) productRepository.findAll();

        assertThat(products).isEmpty();
    }

    @Test
    public void findProductsByCategory_ReturnListOfProducts_whenFindAllSuccess() {
        Product newProduct1 = Generators.validProduct1();
        Product newProduct2 = Generators.validProduct2();
        productRepository.save(newProduct1);
        productRepository.save(newProduct2);

        List<Product> products = (List<Product>) productRepository.findAllByType(newProduct1.getType());

        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(1);
    }
    @Test
    public void findAll_ReturnEmptylist_whenProductsDoesntExists() {
        Product newProduct1 = Generators.validProduct1();
        Product newProduct2 = Generators.validProduct2();
        productRepository.save(newProduct1);
        List<Product> products = (List<Product>) productRepository.findAllByType(newProduct2.getType());

        assertThat(products).isEmpty();
    }
}
