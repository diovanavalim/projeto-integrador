package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.exception.InternalServerErrorException;
import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Section;
import dh.meli.projeto_integrador.repository.ISectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Method to find a section by id;
     * @param id of type long. Section identifier;
     * @return an object of type Section;
     */
    @Override
    public Section findSection(long id) {
        Optional<Section> section = sectionRepository.findById(id);

        if (section.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Could not find valid section for id %d", id));
        }

        return section.get();
    }

    /**
     * Method to save a new section;
     * @param section of type Section. Section instance;
     * @return an object of type Section;
     */
    public Section saveSection(Section section) {
        try {
            return sectionRepository.save(section);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
