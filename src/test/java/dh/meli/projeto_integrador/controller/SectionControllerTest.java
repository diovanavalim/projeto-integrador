package dh.meli.projeto_integrador.controller;

import dh.meli.projeto_integrador.dto.dtoInput.CreateSectionDto;
import dh.meli.projeto_integrador.dto.dtoOutput.SectionDtoOutput;
import dh.meli.projeto_integrador.model.Section;
import dh.meli.projeto_integrador.service.SectionService;
import dh.meli.projeto_integrador.util.Generators;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SectionControllerTest {

    @InjectMocks
    SectionController sectionController;

    @Mock
    SectionService sectionService;

    @Test
    void createSectionTest() {
        BDDMockito.when(sectionService.createSection(ArgumentMatchers.any(CreateSectionDto.class)))
                .thenReturn(Generators.getSection());

        ResponseEntity<SectionDtoOutput> response = sectionController.createSection(Generators.getCreateSectionDto());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getWarehouseId()).isEqualTo(Generators.getSection().getWarehouse().getId());
        assertThat(response.getBody().getProductType()).isEqualTo(Generators.getSection().getProductType());
        assertThat(response.getBody().getMaxProductLoad()).isEqualTo(Generators.getSection().getMaxProductLoad());

        verify(sectionService, atLeastOnce()).createSection(ArgumentMatchers.any(CreateSectionDto.class));
    }

    @Test
    void getSectionsTest() {
        List<Section> sectionList = new ArrayList<Section>();

        sectionList.add(Generators.getSection());

        BDDMockito.when(sectionService.getSections())
                .thenReturn(sectionList);

        ResponseEntity<List<SectionDtoOutput>> response = sectionController.getSections();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get(0).getWarehouseId()).isEqualTo(Generators.getSection().getWarehouse().getId());
        assertThat(response.getBody().get(0).getProductType()).isEqualTo(Generators.getSection().getProductType());
        assertThat(response.getBody().get(0).getMaxProductLoad()).isEqualTo(Generators.getSection().getMaxProductLoad());

        verify(sectionService, atLeastOnce()).getSections();
    }

    @Test
    void getSectionTest() {
        BDDMockito.when(sectionService.getSectionById(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getSection());

        ResponseEntity<SectionDtoOutput> response = sectionController.getSection(Generators.getSection().getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMaxProductLoad()).isEqualTo(Generators.getSection().getMaxProductLoad());
        assertThat(response.getBody().getWarehouseId()).isEqualTo(Generators.getSection().getWarehouse().getId());
        assertThat(response.getBody().getProductType()).isEqualTo(Generators.getSection().getProductType());

        verify(sectionService, atLeastOnce()).getSectionById(ArgumentMatchers.anyLong());
    }
}
