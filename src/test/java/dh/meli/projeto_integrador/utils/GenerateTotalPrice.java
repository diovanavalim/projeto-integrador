package dh.meli.projeto_integrador.utils;

import dh.meli.projeto_integrador.dto.dtoOutput.TotalPriceDto;

public class GenerateTotalPrice {

    public static TotalPriceDto newTotalPrice() {
        return TotalPriceDto.builder()
                .totalPrice(4.99)
                .build();
    }
}
