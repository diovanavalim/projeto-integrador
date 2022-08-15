package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.CreateSectionDto;
import dh.meli.projeto_integrador.exception.InternalServerErrorException;
import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Section;
import dh.meli.projeto_integrador.model.Warehouse;
import dh.meli.projeto_integrador.repository.ISectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class responsible for business rules and communication with the Section Repository layer;
 * @author Diovana Valim;
 * @version 0.0.1
 */
@Service
public class SectionService implements ISectionService {

    /**
     * Dependency Injection of the Section Repository.
     */
    @Autowired
    private ISectionRepository sectionRepository;

    @Autowired
    private WarehouseService warehouseService;

    /**
     * Method to find a section by id;
     * @param id of type long. Section identifier;
     * @return an object of type Section;
     */
    @Override
    public Section getSectionById(long id) {
        Optional<Section> section;

        try {
            section = sectionRepository.findById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        if (section.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Could not find valid section for id %d", id));
        }

        return section.get();
    }

    /**
     * Method to get all sections stored in application's database;
     * @return a List of type Section;
     */
    @Override
    public List<Section> getSections() {
        Iterable<Section> sectionIterable;

        try {
            sectionIterable = sectionRepository.findAll();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        List<Section> sectionList = new ArrayList<Section>();

        sectionIterable.forEach(sectionList::add);

        return sectionList;
    }

    /**
     * Method to save a new section;
     * @param section of type Section. Section instance;
     * @return an object of type Section;
     */
    @Override
    public Section saveSection(Section section) {
        try {
            return sectionRepository.save(section);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Method that given a CreateSectionDto objects call saveSection function to save a new Section entry;
     * @param sectionDto of type SectionDto. SectionDto instance;
     * @return an object of type Section;
     */
    @Override
    public Section createSection(CreateSectionDto sectionDto) {
        Warehouse warehouse = warehouseService.findWarehouse(sectionDto.getWarehouseId());

        Section section = new Section();

        section.setProductType(sectionDto.getProductType());
        section.setWarehouse(warehouse);
        section.setMaxProductLoad(sectionDto.getMaxProductLoad());
        section.setCurrentProductLoad(0);

        return this.saveSection(section);
    }
}
