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

@RestController
@RequestMapping("/api/v1")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("/warehouse")
    public ResponseEntity<WarehouseOutputDto> createWarehouse(@RequestBody @Valid WarehouseInputDto warehouse) {
        Warehouse createdWarehouse = warehouseService.createWarehouse(warehouse);

        return new ResponseEntity<WarehouseOutputDto>(new WarehouseOutputDto(createdWarehouse), HttpStatus.CREATED);
    }

    @GetMapping("/warehouse/{id}")
    public ResponseEntity<WarehouseOutputDto> getWarehouse(@PathVariable @Valid long id) {
        Warehouse warehouse = warehouseService.findWarehouse(id);

        WarehouseOutputDto warehouseOutputDto = new WarehouseOutputDto(warehouse);

        warehouseOutputDto.setSections(warehouseService.setWarehouseSections(warehouse));
        warehouseOutputDto.setAgents(warehouseService.setWarehouseAgents(warehouse));

        return new ResponseEntity<WarehouseOutputDto>(warehouseOutputDto, HttpStatus.OK);
    }

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
