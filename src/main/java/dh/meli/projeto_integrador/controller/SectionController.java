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

@RestController
@RequestMapping("/api/v1")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @PostMapping("/section")
    public ResponseEntity<SectionDtoOutput> createSection(@RequestBody @Valid CreateSectionDto sectionDto) {
        Section createdSection = sectionService.createSection(sectionDto);

        return new ResponseEntity<SectionDtoOutput>(new SectionDtoOutput(createdSection), HttpStatus.CREATED);
    }

    @GetMapping("/section")
    public ResponseEntity<List<SectionDtoOutput>> getSections() {
        List<Section> sectionList = sectionService.getSections();

        List<SectionDtoOutput> sectionDtoOutputList = new ArrayList<SectionDtoOutput>();

        sectionList.forEach(section -> {
            sectionDtoOutputList.add(new SectionDtoOutput(section));
        });

        return new ResponseEntity<List<SectionDtoOutput>>(sectionDtoOutputList, HttpStatus.OK);
    }

    @GetMapping("/section/{id}")
    public ResponseEntity<SectionDtoOutput> getSection(@PathVariable long id) {
        Section section = sectionService.getSectionById(id);

        return new ResponseEntity<SectionDtoOutput>(new SectionDtoOutput(section), HttpStatus.OK);
    }
}
