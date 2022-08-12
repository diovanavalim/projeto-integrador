package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.model.Section;

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
    Section findSection(long id);
}
