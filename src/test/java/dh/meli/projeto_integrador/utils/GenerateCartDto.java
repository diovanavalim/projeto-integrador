package dh.meli.projeto_integrador.utils;

import dh.meli.projeto_integrador.dto.dtoInput.CartDto;
import dh.meli.projeto_integrador.enumClass.PurchaseOrderStatusEnum;

import java.time.LocalDate;

public class GenerateCartDto {

    public static CartDto newCartDto() {
        return CartDto.builder()
                .date(LocalDate.now())
                .buyerId(1L)
                .orderStatus(PurchaseOrderStatusEnum.OPEN)
                .products(GenerateProduct.newProductListDto())
                .build();
    }

    public static CartDto newCartDtoFinished() {
        return CartDto.builder()
                .date(LocalDate.now())
                .buyerId(1L)
                .orderStatus(PurchaseOrderStatusEnum.FINISHED)
                .products(GenerateProduct.newProductListDto())
                .build();
    }
}
