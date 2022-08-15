package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.SectionDto;
import dh.meli.projeto_integrador.dto.dtoInput.WarehouseInputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.AgentDto;
import dh.meli.projeto_integrador.model.Warehouse;

import java.util.List;

/**
 * Interface to specify service methods implemented on WarehouseService class.
 * @author Diovana Valim, Thiago Guimaraes;
 * @version 0.0.1
 */
public interface IWarehouseService {
    /**
     * Method to create a new warehouse entry;
     * @param warehouseInputDto of type WarehouseInputDto;
     * @return an object of type Warehouse;
     */
    Warehouse createWarehouse(WarehouseInputDto warehouseInputDto);

    /**
     * Method for to find Warehouse by Id
     * @param id long
     * @return an object of type Warehouse
     */
    Warehouse findWarehouse(long id);

    /**
     * Method to find all warehouses
     * @return a List of type Warehouse;
     */
    List<Warehouse> findWarehouses();

    /**
     * Method to get warehouse's Sections into SectionDto
     * @param warehouse of type Warehouse
     * @return a List of type SectionDto
     */
    List<SectionDto> setWarehouseSections(Warehouse warehouse);

    /**
     * Method to get warehouse's Agents into AgentDto
     * @param warehouse of type Warehouse
     * @return a List of type AgentDto
     */
    List<AgentDto> setWarehouseAgents(Warehouse warehouse);
}
