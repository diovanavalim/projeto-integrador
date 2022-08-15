package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoOutput.ProductStockDto;
import dh.meli.projeto_integrador.dto.dtoOutput.ProductOutputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.ListProductByWarehouseDto;
import dh.meli.projeto_integrador.model.Product;

import java.util.List;

/**
 * Interface to specify service methods implemented on ProductService class.
 * @author Rafael Cavalcante
 * @version 0.0.1
 */
public interface IProductService {
    /**
     * Method for to get all products
     * @return a list of objects of type ProductOutputDto
     */
    List<ProductOutputDto> getAllProducts();

    /**
     * Method for to get products by category
     * @param category String
     * @return a list of output objects of type ProductOutputDto
     */
    List<ProductOutputDto> getProductsByCategory(String category);

    /**
     * Method for to find product by id
     * @param id long
     * @return an object of type Product
     */
    Product findProduct(long id);

     /**
     * Method to find a product by id and return some properties about the batches
     * @param id Long
     * @param order Character
     * @return an object of type ProductStockDto
     */
    ProductStockDto getProductBatchProps(Long id,Character order);

    /**
     * Method for to list all products by warehouse
     * @param productId long id received user request
     * @return an output object of type ListProductByWarehouseDto
     */
    ListProductByWarehouseDto listProductByWarehouse(long productId);
}
