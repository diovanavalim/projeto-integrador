package dh.meli.projeto_integrador.utils;

import dh.meli.projeto_integrador.dto.dtoOutput.UpdateStatusDto;

public class GenerateUpdateStatusDto {

    public static UpdateStatusDto newUpdateStatusDto() {
        return UpdateStatusDto.builder()
                .message("Cart Finished successfully")
                .build();
    }
}
