package dh.meli.projeto_integrador.controller;

import dh.meli.projeto_integrador.dto.dtoInput.CreateSectionDto;
import dh.meli.projeto_integrador.dto.dtoOutput.SectionDtoOutput;
import dh.meli.projeto_integrador.model.Section;
import dh.meli.projeto_integrador.service.SectionService;
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
public class SectionController {

    /**
     * Dependency Injection of the SectionService.
     */
    @Autowired
    private SectionService sectionService;

    /**
     * A post method responsible for saving a new Section at application database's;
     * @param sectionDto a valid CreateSectionDto instance received by the request body;
     * @return Response Entity of type SectionDtoOutput and the corresponding HttpStatus;
     */
    @PostMapping("/section")
    public ResponseEntity<SectionDtoOutput> createSection(@RequestBody @Valid CreateSectionDto sectionDto) {
        Section createdSection = sectionService.createSection(sectionDto);

        return new ResponseEntity<SectionDtoOutput>(new SectionDtoOutput(createdSection), HttpStatus.CREATED);
    }

    /**
     * A get method responsible for returning all sections at application database's;
     * @return Response Entity of type List of SectionDtoOutput and the corresponding HttpStatus;
     */
    @GetMapping("/section")
    public ResponseEntity<List<SectionDtoOutput>> getSections() {
        List<Section> sectionList = sectionService.getSections();

        List<SectionDtoOutput> sectionDtoOutputList = new ArrayList<SectionDtoOutput>();

        sectionList.forEach(section -> {
            sectionDtoOutputList.add(new SectionDtoOutput(section));
        });

        return new ResponseEntity<List<SectionDtoOutput>>(sectionDtoOutputList, HttpStatus.OK);
    }

    /**
     * A get method responsible for returning a single Section by its ID;
     * @param id section's identifier;
     * @return Response Entity of type SectionDtoOutput and the corresponding HttpStatus;
     */
    @GetMapping("/section/{id}")
    public ResponseEntity<SectionDtoOutput> getSection(@PathVariable long id) {
        Section section = sectionService.getSectionById(id);

        return new ResponseEntity<SectionDtoOutput>(new SectionDtoOutput(section), HttpStatus.OK);
    }
}
