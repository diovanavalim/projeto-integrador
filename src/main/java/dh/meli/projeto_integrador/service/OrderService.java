package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.OrderEntryDto;
import dh.meli.projeto_integrador.dto.dtoInput.BatchDto;
import dh.meli.projeto_integrador.exception.ForbiddenException;
import dh.meli.projeto_integrador.exception.InternalServerErrorException;
import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.*;
import dh.meli.projeto_integrador.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.*;

/**
 * Class responsible for business rules and communication with the OrderEntry Repository layer;
 * @author Diovana Valim, Thiago Almeida;
 * @version 0.0.2
 */
@Service
public class OrderService implements IOrderService {

    /**
     * Dependency Injection of the OrderEntry Repository.
     */
    @Autowired
    private IOrderRepository orderRepository;

    /**
     * Dependency Injection of the Product Service.
     */
    @Autowired
    private ProductService productService;

    /**
     * Dependency Injection of the Warehouse Service.
     */
    @Autowired
    private WarehouseService warehouseService;

    /**
     * Dependency Injection of the Section Service.
     */
    @Autowired
    private SectionService sectionService;

    /**
     * Dependency Injection of the Agent Service.
     */
    @Autowired
    private AgentService agentService;

    /**
     * Dependency Injection of the Batch Service.
     */
    @Autowired
    private BatchService batchService;

    /**
     * Method to save new Inbound Order. Validates Warehouse, Section and Agent before inserting into
     * application database;
     * @param orderEntryDto of type OrderEntryDto. OrderEntry POJO;
     * @return a Set of Batches;
     */
    @Transactional
    @Override
    public List<BatchDto> createInboundOrder(OrderEntryDto orderEntryDto) {
        Warehouse warehouse = warehouseService.findWarehouse(orderEntryDto.getSection().getWarehouseId());

        Section section = sectionService.getSectionById(orderEntryDto.getSection().getSectionId());

        Agent agent = agentService.findAgent(orderEntryDto.getAgentId());

        long agentWarehouseId = agent.getWarehouse().getId();

        if (agentWarehouseId != warehouse.getId()) {
            throw new ForbiddenException("Agent's warehouse ID does not belong to section's warehouse ID");
        }

        if (section.getWarehouse().getId() != warehouse.getId()) {
            throw new ForbiddenException("Section does not belong to the given warehouse");
        }

        OrderEntry orderEntry = new OrderEntry();

        orderEntry.setSection(section);
        orderEntry.setOrderDate(orderEntryDto.getOrderDate());

        Set<Batch> batches = new HashSet<>();

        int finalQuantity = 0;

        for (BatchDto batchDto : orderEntryDto.getBatchStock()) {
            Batch batch = new Batch();

            Product product = productService.findProduct(batchDto.getProductId());

            String sectionProductType = orderEntry.getSection().getProductType();

            String productType = product.getType();

            // Validates if product's section equals request given section
            if (!productType.equals(sectionProductType)) {
                throw new ForbiddenException(String.format("Product's %s section does not equals the given section",
                        product.getName()));
            }

            batch.setCurrentTemperature(batchDto.getCurrentTemperature());
            batch.setMinimumTemperature(batchDto.getMinimumTemperature());
            batch.setInitialQuantity(batchDto.getInitialQuantity());
            batch.setCurrentQuantity(batchDto.getCurrentQuantity());
            batch.setManufacturingDate(batchDto.getManufacturingDate());
            batch.setManufacturingTime(batchDto.getManufacturingTime());
            batch.setDueDate(batchDto.getDueDate());
            batch.setProduct(product);
            batch.setOrderEntry(orderEntry);

            batches.add(batch);
            finalQuantity += batch.getInitialQuantity();
        }

        orderEntry.setBatches(batches);

        // Validates if wanted section has available space for storing new products
        if (section.getCurrentProductLoad() + finalQuantity > section.getMaxProductLoad()) {
            throw new ForbiddenException("Product batches quantity sum overtakes section maximum product load");
        }

        try {
            orderRepository.save(orderEntry);
            batches.forEach(batchService::createBatch);

            section.setCurrentProductLoad(section.getCurrentProductLoad() + finalQuantity);
            sectionService.saveSection(section);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        List<BatchDto> batchDtoList = new ArrayList<BatchDto>();

        batches.forEach(batch -> {
            batchDtoList.add(new BatchDto(batch));
        });

        return batchDtoList;
    }

    /**
     * Method to replace(delete old and create new) Inbound Order. Validates if given id is valid
     * Also Validates Warehouse, Section and Agent before inserting into application database;
     * @param orderEntryDto of type OrderEntryDto. OrderEntry POJO;
     * @param id of type Long. OrderEntry identifier
     * @return a Set of Batches;
     */
    @Transactional
    public BatchDto updateInboundOrder(OrderEntryDto orderEntryDto, Long id) {
        OrderEntry foundOrder = findOrderEntry(id); // Checks if the order entry with a given id exists
        List<Batch> batchList = batchService.findAllByOrderEntry(foundOrder);
        Section section = foundOrder.getSection();

        updateSectionProductLoadBeforeOrderDelete(batchList, section);
        delete(id); // Deletes the old order entry

        return createInboundOrder(orderEntryDto).get(0); // Replaces it with a newly created one
    }

    /**
     * Method to find an order entry given an id.
     * Validates if given id is valid.
     * @param id of type Long. OrderEntry identifier
     * @return an object of type OrderEntry;
     */
    public OrderEntry findOrderEntry(long id) {
        Optional<OrderEntry> orderEntry = orderRepository.findById(id);
        if (orderEntry.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Could not find valid order entry for id %d", id));
        }

        return orderEntry.get();
    }

    /**
     * Method to delete an order entry given an id.
     * Validates if given id is valid.
     * @param id of type Long. OrderEntry identifier
     * @return the deleted order entry. An object of type OrderEntry;
     */
    public OrderEntry delete(long id) {
        Optional<OrderEntry> orderEntry = orderRepository.findById(id);
        if (orderEntry.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Could not find valid order entry for id %d", id));
        }
        orderRepository.delete(orderEntry.get());

        return orderEntry.get();
    }

    /**
     * Method to update the productLoad attribute of a given Section class before deleting an order entry instance.
     * Decrements the productLoad in a section given all the batch stocks quantities that were stored in that section
     * that came from the same order entry.
     * @param batchList: List of objects of type Batch. Iterable list
     * @param section an instance of Section. Section identifier
     */
    static private void updateSectionProductLoadBeforeOrderDelete (List<Batch> batchList, Section section) {
        long currentProductLoad = section.getCurrentProductLoad();
        for (Batch batch : batchList) {
            currentProductLoad -= batch.getCurrentQuantity();
        }
        section.setCurrentProductLoad(currentProductLoad);
    }
}

