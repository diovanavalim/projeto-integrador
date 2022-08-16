package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.CreateSectionDto;
import dh.meli.projeto_integrador.exception.InternalServerErrorException;
import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Section;
import dh.meli.projeto_integrador.repository.ISectionRepository;
import dh.meli.projeto_integrador.util.Generators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SectionServiceTest {

    @InjectMocks
    SectionService sectionService;

    @Mock
    ISectionRepository sectionRepository;

    @Mock
    WarehouseService warehouseService;

    @BeforeEach
    void setup() {
        BDDMockito.when(sectionRepository.save(ArgumentMatchers.any(Section.class)))
                .thenReturn(Generators.getSection());
        BDDMockito.when(sectionRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(Generators.getSection()));
    }

    @Test
    void getSectionByIdTest() {
        long id = 0;
        Section section = sectionService.getSectionById(id);

        assertThat(section.getId()).isEqualTo(Generators.getSection().getId());
        assertThat(section.getWarehouse().getId()).isEqualTo(Generators.getSection().getWarehouse().getId());
        assertThat(section.getProductType()).isEqualTo(Generators.getSection().getProductType());

        verify(sectionRepository, atLeastOnce()).findById(id);
    }

    @Test
    void getSectionById_WhenSectionDontExistsTest() throws Exception {
        BDDMockito.when(sectionRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        long id = 0;

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Section section = sectionService.getSectionById(id);
        });

        assertThat(exception.getMessage()).isEqualTo(String.format("Could not find valid section for id %d", id));

        verify(sectionRepository, atLeastOnce()).findById(id);
    }

    @Test
    void getSectionById_WhenDatabaseIsUnavailable() throws Exception {
        BDDMockito.when(sectionRepository.findById(ArgumentMatchers.anyLong()))
                .thenThrow(InternalServerErrorException.class);

        long id = 1;

        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
            Section section = sectionService.getSectionById(id);
        });

        verify(sectionRepository, atLeastOnce()).findById(ArgumentMatchers.anyLong());
    }

    @Test
    void getSectionsTest() {
        List<Section> sectionList = new ArrayList<Section>();

        sectionList.add(Generators.getSection());

        BDDMockito.when(sectionRepository.findAll()).thenReturn(sectionList);

        List<Section> response = sectionService.getSections();

        assertThat(response.get(0).getMaxProductLoad()).isEqualTo(sectionList.get(0).getMaxProductLoad());
        assertThat(response.get(0).getProductType()).isEqualTo(sectionList.get(0).getProductType());
        assertThat(response.get(0).getWarehouse().getId()).isEqualTo(sectionList.get(0).getWarehouse().getId());

        verify(sectionRepository, atLeastOnce()).findAll();
    }

    @Test
    void getSectionsTest_WhenDatabaseIsUnavailable() throws Exception {
        BDDMockito.when(sectionRepository.findAll()).thenThrow(InternalServerErrorException.class);

        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
           List<Section> sectionList = sectionService.getSections();
        });

        verify(sectionRepository, atLeastOnce()).findAll();
    }

    @Test
    void saveSectionTest() {
        Section section = Generators.getSection();
        Section responseSection = sectionService.saveSection(section);

        assertThat(responseSection.getId()).isEqualTo(section.getId());
        assertThat(responseSection.getWarehouse().getId()).isEqualTo(section.getWarehouse().getId());
        assertThat(responseSection.getProductType()).isEqualTo(section.getProductType());

        verify(sectionRepository, atLeastOnce()).save(section);
    }

    @Test
    void saveSectionTest_WhenDatabaseIsUnavailable() throws Exception {
        BDDMockito.when(sectionRepository.save(ArgumentMatchers.any(Section.class)))
                .thenThrow(InternalServerErrorException.class);

        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () -> {
            Section section = sectionService.saveSection(Generators.getSection());
        });

        verify(sectionRepository, atLeastOnce()).save(ArgumentMatchers.any(Section.class));
    }

    @Test
    void createSectionTest() {
        BDDMockito.when(warehouseService.findWarehouse(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getSection().getWarehouse());

        Section section = Generators.getSection();

        CreateSectionDto sectionDto = new CreateSectionDto(section.getWarehouse().getId(),
                section.getProductType(), section.getMaxProductLoad());

        Section response = sectionService.createSection(sectionDto);

        assertThat(response.getProductType()).isEqualTo(sectionDto.getProductType());
        assertThat(response.getMaxProductLoad()).isEqualTo(sectionDto.getMaxProductLoad());
        assertThat(response.getWarehouse().getId()).isEqualTo(sectionDto.getWarehouseId());
    }
}
