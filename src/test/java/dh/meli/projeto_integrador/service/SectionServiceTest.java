package dh.meli.projeto_integrador.service;

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
    void saveSectionTest() {
        Section section = Generators.getSection();
        Section responseSection = sectionService.saveSection(section);

        assertThat(responseSection.getId()).isEqualTo(section.getId());
        assertThat(responseSection.getWarehouse().getId()).isEqualTo(section.getWarehouse().getId());
        assertThat(responseSection.getProductType()).isEqualTo(section.getProductType());

        verify(sectionRepository, atLeastOnce()).save(section);
    }
}
