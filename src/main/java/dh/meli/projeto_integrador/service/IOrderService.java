package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.BatchDto;
import dh.meli.projeto_integrador.dto.dtoInput.OrderEntryDto;

import java.util.List;

/**
 * Interface to specify service methods implemented on OrderService class.
 * @author Diovana Valim, Thiago Guimaraes;
 * @version 0.0.1
 */
public interface IOrderService {
    List<BatchDto> createInboundOrder(OrderEntryDto orderEntryDto);
}
