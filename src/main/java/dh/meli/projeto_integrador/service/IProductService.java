package dh.meli.projeto_integrador.service;

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
    List<ProductOutputDto> getAllProducts();
    List<ProductOutputDto> getProductsByCategory(String category);
    Product findProduct(long id);
    ListProductByWarehouseDto listProductByWarehouse(long productId);
}
