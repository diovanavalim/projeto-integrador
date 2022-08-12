package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.model.Warehouse;

/**
 * Interface to specify service methods implemented on WarehouseService class.
 * @author Diovana Valim, Thiago Guimaraes;
 * @version 0.0.1
 */
public interface IWarehouseService {
    /**
     * Method for to find Warehouse by Id
     * @param id long
     * @return an object of type Warehouse
     */
    Warehouse findWarehouse(long id);
}
