package br.com.letscode.starwars.service;

import br.com.letscode.starwars.model.DTO.InventoryAVG;
import br.com.letscode.starwars.model.DTO.OverviewResponse;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.model.Entity.Traitor;
import br.com.letscode.starwars.repository.RebelsInventoryRepository;
import br.com.letscode.starwars.repository.RebelsRepository;
import br.com.letscode.starwars.repository.TraitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class OverviewServiceTest {
    @InjectMocks
    OverviewService overviewService;

    @Mock
    RebelsRepository repositoryRebel;
    @Mock
    TraitorRepository repositoryTraitor;
    @Mock
    RebelsInventoryRepository repositoryInventory;

    @Mock
    OverviewResponse overviewResponse;

    @BeforeEach
    void setUp() {
        var rebel1 = Rebel
                .builder()
                .rebel(1L)
                .build();
        var rebel2 = Rebel
                .builder()
                .rebel(2L)
                .build();

        var traitor = new Traitor(1L, 1L);

        var inventoryAvg = new InventoryAVG(1.0,0.0, 0.0, 0.0);

        overviewResponse = OverviewResponse
                .builder()
                .itemsByRebel(inventoryAvg)
                .lostPoints(0)
                .percentRebel(0.5f)
                .percentTraitor(0.5f)
                .build();

        Mockito.when(repositoryRebel.count()).thenReturn(2L);
        Mockito.when(repositoryTraitor.count()).thenReturn(1L);
        Mockito.when(repositoryTraitor.findAll()).thenReturn(List.of(traitor));
        Mockito.when(repositoryRebel.findAllById(List.of(1L))).thenReturn(List.of(rebel1));
        Mockito.when(repositoryInventory.calculateAVG()).thenReturn(inventoryAvg);
    }

    @Test
    @DisplayName("Deve validar se o retorno do relatório.")
    void shouldGenerateOverviewWithNotNullData() {
        var response = overviewService.generateResume();
        assertEquals(response, overviewResponse);
    }

}