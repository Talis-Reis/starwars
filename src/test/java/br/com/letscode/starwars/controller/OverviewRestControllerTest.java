package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.DTO.InventoryAVG;
import br.com.letscode.starwars.model.DTO.OverviewResponse;
import br.com.letscode.starwars.service.OverviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
class OverviewRestControllerTest {

    @InjectMocks
    OverviewRestController restController;

    @Mock
    OverviewService overviewService;

    @Mock
    OverviewResponse overview;


    @BeforeEach
    void setup() {
        overview = OverviewResponse
                .builder()
                .percentRebel(1.0f)
                .percentTraitor(1.0f)
                .itemsByRebel(new InventoryAVG(1.0, 1.0, 1.0, 1.0))
                .lostPoints(0)
                .build();
    }

    @Test
    @DisplayName("Deve retornar o overview.")
    void shouldGenerateOverview() {
        Mockito.when(overviewService.generateResume()).thenReturn(overview);

        var response = restController.generateOverview();

        assertNotNull(response);
        assertEquals(response, overview);
    }
}