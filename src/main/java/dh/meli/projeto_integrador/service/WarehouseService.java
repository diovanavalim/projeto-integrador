package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.SectionDto;
import dh.meli.projeto_integrador.dto.dtoInput.WarehouseInputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.AgentDto;
import dh.meli.projeto_integrador.exception.InternalServerErrorException;
import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Warehouse;
import dh.meli.projeto_integrador.repository.IWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class responsible for business rules and communication with the Warehouse Repository layer;
 * @author Diovana Valim;
 * @version 0.0.1
 */
@Service
public class WarehouseService implements IWarehouseService {

    /**
     * Dependency Injection of the Warehouse Repository.
     */
    @Autowired
    private IWarehouseRepository warehouseRepository;

    /**
     * Method to create a new warehouse entry;
     * @param warehouseInputDto of type WarehouseInputDto;
     * @return an object of type Warehouse;
     */
    @Override
    public Warehouse createWarehouse(WarehouseInputDto warehouseInputDto) {
        try {
            Warehouse warehouse = new Warehouse();

            warehouse.setName(warehouseInputDto.getName());
            warehouse.setAddress(warehouseInputDto.getAddress());

            return warehouseRepository.save(warehouse);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Method to find a warehouse by id;
     * @param id of type long. Warehouse identifier;
     * @return an object of type Warehouse;
     */
    @Override
    public Warehouse findWarehouse(long id) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);

        if (warehouse.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Could not find valid warehouse for id %d", id));
        }

        return warehouse.get();
    }

    /**
     * Method to find all warehouses
     * @return a List of type Warehouse;
     */
    @Override
    public List<Warehouse> findWarehouses() {
        Iterable<Warehouse> warehouseIterable;

        try {
            warehouseIterable = warehouseRepository.findAll();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        List<Warehouse> warehouseList = new ArrayList<Warehouse>();

        warehouseIterable.forEach(warehouseList::add);

        return warehouseList;
    }

    /**
     * Method to get warehouse's Sections into SectionDto
     * @param warehouse of type Warehouse
     * @return a List of type SectionDto
     */
    @Override
    public List<SectionDto> setWarehouseSections(Warehouse warehouse) {
        List<SectionDto> sectionDtoList = new ArrayList<SectionDto>();

        if (warehouse.getSections() != null) {
            warehouse.getSections().forEach(section -> {
                sectionDtoList.add(new SectionDto(section));
            });
        }

        return sectionDtoList;
    }

    /**
     * Method to get warehouse's Agents into AgentDto
     * @param warehouse of type Warehouse
     * @return a List of type AgentDto
     */
    @Override
    public List<AgentDto> setWarehouseAgents(Warehouse warehouse) {
        List<AgentDto> agentDtoList = new ArrayList<AgentDto>();

        if (warehouse.getAgents() != null) {
            warehouse.getAgents().forEach(agent -> {
                agentDtoList.add(new AgentDto(agent));
            });
        }

        return agentDtoList;
    }

}
