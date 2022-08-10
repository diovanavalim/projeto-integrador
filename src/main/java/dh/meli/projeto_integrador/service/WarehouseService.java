package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Warehouse;
import dh.meli.projeto_integrador.repository.IWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
