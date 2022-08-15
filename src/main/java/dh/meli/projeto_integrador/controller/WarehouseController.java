package dh.meli.projeto_integrador.controller;

import dh.meli.projeto_integrador.dto.dtoInput.WarehouseInputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.WarehouseOutputDto;
import dh.meli.projeto_integrador.model.Warehouse;
import dh.meli.projeto_integrador.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for processing user's requests and generating appropriated HTTP responses;
 * @author Diovana Valim
 * @version 0.0.3;
 */
@RestController
@RequestMapping("/api/v1")
public class WarehouseController {

    /**
     * Dependency Injection of the WarehouseService.
     */
    @Autowired
    private WarehouseService warehouseService;

    /**
     * A post method responsible for saving a new Warehouse at application database's;
     * @param warehouseInputDto a valid WarehouseInputDto instance received by the request body;
     * @return Response Entity of type WarehouseOutputDto and the corresponding HttpStatus;
     */
    @PostMapping("/warehouse")
    public ResponseEntity<WarehouseOutputDto> createWarehouse(@RequestBody @Valid WarehouseInputDto warehouseInputDto) {
        Warehouse createdWarehouse = warehouseService.createWarehouse(warehouseInputDto);

        return new ResponseEntity<WarehouseOutputDto>(new WarehouseOutputDto(createdWarehouse), HttpStatus.CREATED);
    }

    /**
     * A get method responsible for returning a single warehouse by its ID;
     * @param id warehouse's identifier;
     * @return Response Entity of type WarehouseOutputDto and the corresponding HttpStatus;
     */
    @GetMapping("/warehouse/{id}")
    public ResponseEntity<WarehouseOutputDto> getWarehouse(@PathVariable @Valid long id) {
        Warehouse warehouse = warehouseService.findWarehouse(id);

        WarehouseOutputDto warehouseOutputDto = new WarehouseOutputDto(warehouse);

        warehouseOutputDto.setSections(warehouseService.setWarehouseSections(warehouse));

        warehouseOutputDto.setAgents(warehouseService.setWarehouseAgents(warehouse));

        return new ResponseEntity<WarehouseOutputDto>(warehouseOutputDto, HttpStatus.OK);
    }

    /**
     * A get method responsible for returning all warehouses at application database's;
     * @return Response Entity of type List of WarehouseOutputDto and the corresponding HttpStatus;
     */
    @GetMapping("/warehouse")
    public ResponseEntity<List<WarehouseOutputDto>> getWarehouses() {
        List<Warehouse> warehouseList = warehouseService.findWarehouses();

        List<WarehouseOutputDto> warehouseOutputDtoList = new ArrayList<WarehouseOutputDto>();

        warehouseList.forEach(warehouse -> {
            WarehouseOutputDto warehouseOutputDto = new WarehouseOutputDto(warehouse);

            warehouseOutputDto.setAgents(warehouseService.setWarehouseAgents(warehouse));

            warehouseOutputDto.setSections(warehouseService.setWarehouseSections(warehouse));

            warehouseOutputDtoList.add(warehouseOutputDto);
        });

        return new ResponseEntity<List<WarehouseOutputDto>>(warehouseOutputDtoList, HttpStatus.OK);
    }
}
