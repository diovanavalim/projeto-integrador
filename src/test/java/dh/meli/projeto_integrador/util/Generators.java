package dh.meli.projeto_integrador.util;

import dh.meli.projeto_integrador.dto.dtoInput.BatchDto;
import dh.meli.projeto_integrador.dto.dtoInput.OrderEntryDto;
import dh.meli.projeto_integrador.dto.dtoInput.SectionDto;
import dh.meli.projeto_integrador.dto.dtoOutput.ListProductByWarehouseDto;
import dh.meli.projeto_integrador.dto.dtoOutput.ProductOutputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.TotalProductByWarehouseDto;
import dh.meli.projeto_integrador.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Generators {

    public static List<BatchDto> createInboundOrder() {
        Batch batch = createBatch();

        List<BatchDto> batches = new ArrayList<BatchDto>();

        batches.add(new BatchDto(batch));

        return batches;
    }

    public static Batch createBatch() {
        Warehouse warehouse = new Warehouse();

        warehouse.setId(1);
        warehouse.setName("Armazém 01");
        warehouse.setAddress("Rua Almeida 259");

        Section section = new Section();

        section.setId(1);
        section.setCurrentProductLoad(130);
        section.setMaxProductLoad(2000);
        section.setProductType("Fresco");
        section.setWarehouse(warehouse);

        HashSet<Section> sections = new HashSet<Section>();

        sections.add(section);

        warehouse.setSections(sections);

        Product product = new Product();
        product.setId(1);
        product.setPrice(12.20);
        product.setName("Maçã");
        product.setType("Fresco");

        HashSet<Product> products = new HashSet<Product>();

        products.add(product);

        OrderEntry orderEntry = new OrderEntry();

        orderEntry.setId(1);
        orderEntry.setOrderDate(LocalDate.now());
        orderEntry.setSection(section);

        HashSet<OrderEntry> orderEntries = new HashSet<OrderEntry>();

        orderEntries.add(orderEntry);

        section.setOrderEntries(orderEntries);

        Batch batch01 = new Batch();

        batch01.setId(1);
        batch01.setCurrentTemperature(10);
        batch01.setMinimumTemperature(0);
        batch01.setInitialQuantity(100);
        batch01.setCurrentQuantity(100);
        batch01.setManufacturingDate(LocalDate.now());
        batch01.setManufacturingTime(LocalTime.now());
        batch01.setDueDate(LocalDate.now());
        batch01.setProduct(product);
        batch01.setOrderEntry(orderEntry);

        HashSet<Batch> batches = new HashSet<Batch>();

        batches.add(batch01);

        orderEntry.setBatches(batches);

        return batch01;
    }

    public static OrderEntryDto createOrderEntryDto() {
        BatchDto batchDto = new BatchDto();

        batchDto.setBatchId(1);
        batchDto.setProductId(1);
        batchDto.setCurrentQuantity(100);
        batchDto.setInitialQuantity(120);
        batchDto.setCurrentTemperature(10);
        batchDto.setMinimumTemperature(0);
        batchDto.setManufacturingDate(LocalDate.now());
        batchDto.setManufacturingTime(LocalTime.now());
        batchDto.setDueDate(LocalDate.now());

        Set<BatchDto> batchDtoSet = new HashSet<BatchDto>();

        batchDtoSet.add(batchDto);

        SectionDto sectionDto = new SectionDto();

        sectionDto.setSectionId(1);
        sectionDto.setWarehouseId(1);

        OrderEntryDto orderEntryDto = new OrderEntryDto();

        orderEntryDto.setSection(sectionDto);
        orderEntryDto.setAgentId(1);
        orderEntryDto.setBatchStock(batchDtoSet);
        orderEntryDto.setOrderDate(LocalDate.now());

        return orderEntryDto;
    }

    public static OrderEntry getOrderEntry() {
        Batch batch = createBatch();

        return batch.getOrderEntry();
    }

    public static Product getProduct() {
        Batch batch = createBatch();

        return batch.getProduct();
    }

    public static Warehouse getWarehouse() {
        Batch batch = createBatch();

        return batch.getOrderEntry().getSection().getWarehouse();
    }

    public static Section getSection() {
        Batch batch = createBatch();

        Section section = new Section();
        section.setId(1);
        section.setProductType("Fresco");
        section.setMaxProductLoad(100);
        section.setCurrentProductLoad(10);

        Set<OrderEntry> orderEntries = new HashSet<OrderEntry>();
        OrderEntry orderEntry = batch.getOrderEntry();
        orderEntries.add(orderEntry);

        section.setOrderEntries(orderEntries);

        return batch.getOrderEntry().getSection();
    }

    public static Section getUnavailableSection() {
        Batch batch = createBatch();

        Section section = batch.getOrderEntry().getSection();

        section.setProductType("Congelado");

        return section;
    }

    public static Agent getAgent() {
        Batch batch = createBatch();

        Agent agent = new Agent();
        agent.setId(1);
        agent.setName("João Maria");
        agent.setEmailAddress("joao.maria@gmail.com");
        agent.setPhoneNumber("(48) 999876544");
        agent.setWarehouse(batch.getOrderEntry().getSection().getWarehouse());

        Warehouse warehouse = batch.getOrderEntry().getSection().getWarehouse();

        Set<Agent> agents = new HashSet<Agent>();

        agents.add(agent);

        warehouse.setAgents(agents);

        return agent;
    }

    public static Agent getUnavailableAgent() {
        Agent agent = getAgent();

        Warehouse warehouse = new Warehouse();
        warehouse.setId(10);
        warehouse.setName("Armazém 10");
        warehouse.setAddress("Rua A, 222");

        agent.setWarehouse(warehouse);

        return agent;
    }

    public static Batch getBatch() {
        return createBatch();
    }

    public static List<Batch> getBatches() {
        List<Batch> batchList = new ArrayList<Batch>();

        Batch batch = createBatch();

        batchList.add(batch);

        return batchList;
    }

    public static ProductOutputDto validProductDto1() {
        return ProductOutputDto.builder()
                .name("Maçã")
                .type("Fresco")
                .price(20.1)
                .build();
    }

    public static ProductOutputDto validProductDto2() {
        return ProductOutputDto.builder()
                .name("Uva")
                .type("Fresco")
                .price(20.1)
                .build();
    }

    public static Product validProduct1() {
        return Product.builder()
                .name("Maçã")
                .type("Fresco")
                .price(20.1)
                .build();
    }

    public static Product validProduct2() {
        return Product.builder()
                .name("sorvete")
                .type("Congelado")
                .price(20.1)
                .build();
    }

    public static List<ProductOutputDto> productDtoList() {
        List<ProductOutputDto> productList = new ArrayList<>();
        productList.add(validProductDto1());
        productList.add(validProductDto2());

        return productList;
    }

    public static List<Product> productList() {
        List<Product> productList = new ArrayList<>();
        productList.add(validProduct1());
        productList.add(validProduct2());

        return productList;
    }

    public static List<Product> emptyProductDtoList() {
        List<Product> productList = new ArrayList<>();
        return productList;
    }

    public static ListProductByWarehouseDto getListProductByWarehouseDto() {
        List<TotalProductByWarehouseDto> totalProductByWarehouseDtoList = new ArrayList<TotalProductByWarehouseDto>();

        TotalProductByWarehouseDto totalProductByWarehouseDto01 = new TotalProductByWarehouseDto(1, 100);

        totalProductByWarehouseDtoList.add(totalProductByWarehouseDto01);

        return new ListProductByWarehouseDto(1, totalProductByWarehouseDtoList);
    }
}
