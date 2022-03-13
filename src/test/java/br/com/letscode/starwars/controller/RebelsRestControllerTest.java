package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.DTO.*;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.service.PunctuationService;
import br.com.letscode.starwars.service.RebelsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class RebelsRestControllerTest {

    @Mock
    PunctuationService punctuationService;
    @Mock
    RebelsService rebelsService;
    @Mock
    RebelsCreatedResponse rebelsCreatedResponse;
    @Mock
    CreateRebelsRequest rebelsRequest;

    @Mock
    List<Rebel> rebelList;
    @Mock
    Rebel rebel;

    @Mock
    ChangeRebelsRequest changeRebelsRequest;
    @Mock
    ChangeRebelsRequest changeRebelsRequestPartially;

    @Mock
    ChangeRebelResponse changeRebelResponse;
    @Mock
    ChangeRebelResponse partiallyChangedRebelResponse;

    @BeforeEach
    void setup() {
        rebelsRequest =
                CreateRebelsRequest
                        .builder()
                        .name("Marcos")
                        .age(28)
                        .genre("masculino")
                        .baseName("Vênus")
                        .latitud("2")
                        .longitud("3")
                        .traitor(1)
                        .reportsCounter(3)
                        .build();

        rebelsCreatedResponse =
                RebelsCreatedResponse
                        .builder()
                        .rebel(1L)
                        .name("Marcos")
                        .age(28)
                        .genre("masculino")
                        .baseName("Vênus")
                        .latitud("2")
                        .longitud("3")
                        .traitor(1)
                        .reportsCounter(3)
                        .build();

        rebel = Rebel
                .builder()
                .rebel(1L)
                .name("Marcos")
                .age(28)
                .genre("masculino")
                .baseName("Vênus")
                .latitud("2")
                .longitud("3")
                .traitor(1)
                .reportsCounter(3)
                .build();

        rebelList = new ArrayList<>();
        rebelList.add(rebel);

        changeRebelsRequest = ChangeRebelsRequest
                .builder()
                .name("Marcos")
                .age(28)
                .genre("masculino")
                .baseName("Lua")
                .latitud("2")
                .longitud("3")
                .traitor(1)
                .reportsCounter(3)
                .build();

        changeRebelResponse = ChangeRebelResponse
                .builder()
                .rebel(1L)
                .name("Marcos")
                .age(28)
                .genre("masculino")
                .baseName("Lua")
                .latitud("2")
                .longitud("3")
                .traitor(1)
                .reportsCounter(3)
                .build();

        changeRebelsRequestPartially = ChangeRebelsRequest
                .builder()
                .age(40)
                .baseName("Marte")
                .reportsCounter(5)
                .build();

        partiallyChangedRebelResponse = ChangeRebelResponse
                .builder()
                .rebel(1L)
                .name("Marcos")
                .age(40)
                .genre("masculino")
                .baseName("Marte")
                .latitud("2")
                .longitud("3")
                .traitor(1)
                .reportsCounter(5)
                .build();
    }

    @Test
    @DisplayName("Deve retorna uma resposta de rebelde,que deve ter id, nome, idade, gênero, nome da base, latitude, longitude, traidor e número de reportes.")
    void shouldCreateValidRebelResponseWithoutNullProperties() {
        Mockito.doNothing().when(punctuationService).createPunctuation();
        Mockito.when(rebelsService.create(rebelsRequest)).thenReturn(rebelsCreatedResponse);

        RebelsCreatedResponse response = rebelsService.create(rebelsRequest);

        punctuationService.createPunctuation();

        assertNotNull(punctuationService);
        assertNotNull(response);
        assertNotNull(response.getRebel());


        assertEquals(rebelsRequest.getName(), response.getName());
        assertEquals(rebelsRequest.getAge(), response.getAge());
        assertEquals(rebelsRequest.getGenre(), response.getGenre());
        assertEquals(rebelsRequest.getBaseName(), response.getBaseName());
        assertEquals(rebelsRequest.getLatitud(), response.getLatitud());
        assertEquals(rebelsRequest.getLongitud(), response.getLongitud());
        assertEquals(rebelsRequest.getTraitor(), response.getTraitor());
        assertEquals(rebelsRequest.getReportsCounter(), response.getReportsCounter());
    }

    @Test
    @DisplayName("Deve retornar todos os rebeldes salvos em um repositório.")
    void shouldGetAllRebelsInRepository() {
        Mockito.when(rebelsService.getAllRebels()).thenReturn(rebelList);
        List<Rebel> retornoRebel = rebelsService.getAllRebels();

        assertEquals(retornoRebel, rebelList);
        assertNotNull(retornoRebel);
    }

    @Test
    @DisplayName("Deve retornar um rebelde a partir de seu id.")
    void shouldFindRebelById() {
        Mockito.when(rebelsService.findById(1L)).thenReturn(rebel);

        Rebel foundRebelById = rebelsService.findById(1L);

        assertNotNull(foundRebelById);
        assertEquals(foundRebelById, rebel);
    }

    @Test
    @DisplayName("A resposta do rebelde modificado deve ter as mesmas propriedades do rebelde que contém as propriedades requisitadas para serem modificadas.")
    void shouldChangeRebel() {
        Mockito.when(rebelsService.changeRebel(1L, changeRebelsRequest)).thenReturn(changeRebelResponse);

        ChangeRebelResponse rebelChangedResponse = rebelsService.changeRebel(1L, changeRebelsRequest);

        assertNotNull(rebelChangedResponse);

        assertEquals(rebelChangedResponse.getName(), changeRebelsRequest.getName());
        assertEquals(rebelChangedResponse.getAge(), changeRebelsRequest.getAge());
        assertEquals(rebelChangedResponse.getGenre(), changeRebelsRequest.getGenre());
        assertEquals(rebelChangedResponse.getBaseName(), changeRebelsRequest.getBaseName());
        assertEquals(rebelChangedResponse.getLatitud(), changeRebelsRequest.getLatitud());
        assertEquals(rebelChangedResponse.getLongitud(), changeRebelsRequest.getLongitud());
        assertEquals(rebelChangedResponse.getTraitor(), changeRebelsRequest.getTraitor());
        assertEquals(rebelChangedResponse.getReportsCounter(), changeRebelsRequest.getReportsCounter());
    }

    @Test
    void shouldChangePartiallyTheRebel() {
        Mockito.when(rebelsService.changeParcialRebel(1L, changeRebelsRequestPartially)).thenReturn(partiallyChangedRebelResponse);

        ChangeRebelResponse rebel =  rebelsService.changeParcialRebel(1L, changeRebelsRequestPartially);
        assertNotNull(rebel);
    }
}