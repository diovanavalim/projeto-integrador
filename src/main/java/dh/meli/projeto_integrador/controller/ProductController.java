package dh.meli.projeto_integrador.controller;

import dh.meli.projeto_integrador.dto.dtoOutput.ProductOutputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.ListProductByWarehouseDto;
import dh.meli.projeto_integrador.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class responsible for intermediating the requests sent by the user with the responses provided by the Service;
 * @author Diovana Valim, Rafael Cavalcante
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * A get method that when called will return in the body request a list of products present in the Database
     * @return Response Entity of type List of productDto and the corresponding HttpStatus ;
     */
    @GetMapping("/fresh-products")
    public ResponseEntity<List<ProductOutputDto>> listAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * A get method that when called will return in the body request a list of products of a specified category,
     * present in the Database
     * @param category a String received by the URL request to determine the type of product returned
     * @return Response Entity of type List of productDto and the corresponding HttpStatus ;
     */
    @GetMapping("/fresh-products/{category}")
    public ResponseEntity<List<ProductOutputDto>> listProductByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    /**
     * A get method responsible for listing product stock by warehouse
     * @param productId a valid product entity identifier received by path variable;
     * @return Response Entity of a list which type is ListProductByWarehouseDto and the corresponding HttpStatus;
     */
    @GetMapping("/fresh-products/warehouse/product/{productId}")
    public ResponseEntity<ListProductByWarehouseDto> listProductByWarehouse(@PathVariable long productId) {
        ListProductByWarehouseDto listProductByWarehouseDto = productService.listProductByWarehouse(productId);

        return new ResponseEntity<ListProductByWarehouseDto>(listProductByWarehouseDto, HttpStatus.OK);
    }
}
