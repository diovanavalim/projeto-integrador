package dh.meli.projeto_integrador.controller;

import dh.meli.projeto_integrador.dto.dtoInput.BatchDto;
import dh.meli.projeto_integrador.dto.dtoInput.OrderEntryDto;
import dh.meli.projeto_integrador.service.OrderService;
import dh.meli.projeto_integrador.util.Generators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;

    @BeforeEach
    void setup() {
        BDDMockito.when(orderService.createInboundOrder(ArgumentMatchers.any(OrderEntryDto.class)))
                .thenReturn(Generators.createInboundOrder());
    }

    @Test
    @DisplayName("Insert new Inbound Order")
    void createInboundOrderTest() {
        OrderEntryDto orderEntryDto = Generators.createOrderEntryDto();

        List<BatchDto> batchDtoList = new ArrayList<>(orderEntryDto.getBatchStock());

        ResponseEntity<List<BatchDto>> response = orderController.createInboundOrder(orderEntryDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody().get(0).getBatchId()).isEqualTo(batchDtoList.get(0).getBatchId());

        verify(orderService, atLeastOnce()).createInboundOrder(orderEntryDto);
    }
}
