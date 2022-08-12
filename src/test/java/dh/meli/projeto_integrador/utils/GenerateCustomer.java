package dh.meli.projeto_integrador.utils;

import dh.meli.projeto_integrador.model.Customer;

public class GenerateCustomer {
    public static Customer newCustomer1() {
        return Customer.builder()
                .id(1L)
                .cpf("111.111.111-11")
                .name("Alberto")
                .emailAddress("Alberto@email.com")
                .build();
    }
}
