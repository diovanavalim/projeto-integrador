package dh.meli.projeto_integrador.util;

import dh.meli.projeto_integrador.dto.dtoOutput.TotalPriceDto;

public class GenerateTotalPrice {

    public static TotalPriceDto newTotalPrice() {
        return TotalPriceDto.builder()
                .totalPrice(50.0)
                .build();
    }
}
