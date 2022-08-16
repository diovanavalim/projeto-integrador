package dh.meli.projeto_integrador.util;

import dh.meli.projeto_integrador.model.Batch;

import java.time.LocalDate;
import java.time.LocalTime;

public class GenerateBatch {
    public static Batch newBatch1() {
        return Batch.builder()
                .id(1)
                .currentTemperature(1.0f)
                .minimumTemperature(0.0f)
                .initialQuantity(20)
                .currentQuantity(20)
                .manufacturingDate(LocalDate.of(2022, 3, 15))
                .manufacturingTime(LocalTime.of(12,30))
                .dueDate(LocalDate.of(2023, 3, 15))
                .product(GenerateProduct.newProduct1())
                .build();
    }
    public static Batch newBatchInvalidQuantity() {
        return Batch.builder()
                .id(1)
                .currentTemperature(1.0f)
                .minimumTemperature(0.0f)
                .initialQuantity(20)
                .currentQuantity(0)
                .manufacturingDate(LocalDate.of(2022, 3, 15))
                .manufacturingTime(LocalTime.of(12,30))
                .dueDate(LocalDate.of(2023, 3, 15))
                .product(GenerateProduct.newProduct1())
                .build();
    }
}
