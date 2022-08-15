package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.CreateSectionDto;
import dh.meli.projeto_integrador.model.Section;

import java.util.List;

/**
 * Interface to specify service methods implemented on SectionService class.
 * @author Diovana Valim, Thiago Guimaraes;
 * @version 0.0.1
 */
public interface ISectionService {
    /**
     * Method for to find a Section by Id
     * @param id long
     * @return an object of type Section
     */
    Section getSectionById(long id);

    /**
     * Method to save a new section;
     * @param section of type Section. Section instance;
     * @return an object of type Section;
     */
    Section saveSection(Section section);

    /**
     * Method that given a CreateSectionDto objects call saveSection function to save a new Section entry;
     * @param sectionDto of type SectionDto. SectionDto instance;
     * @return an object of type Section;
     */
    Section createSection(CreateSectionDto sectionDto);

    /**
     * Method to get all sections stored in application's database;
     * @return a List of type Section;
     */
    List<Section> getSections();
}
