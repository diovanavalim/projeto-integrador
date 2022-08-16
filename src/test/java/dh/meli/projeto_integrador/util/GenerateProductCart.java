package dh.meli.projeto_integrador.util;

import dh.meli.projeto_integrador.model.ProductCart;

public class GenerateProductCart {
    public static ProductCart newProductCart1() {
        return ProductCart.builder()
                .id(1L)
                .quantity(50)
                .build();
    }
}
