package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.DTO.ChangeRebelResponse;
import br.com.letscode.starwars.model.DTO.ChangeRebelsRequest;
import br.com.letscode.starwars.model.DTO.CreateRebelsRequest;
import br.com.letscode.starwars.model.DTO.RebelsCreatedResponse;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.service.RebelsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class RebelsRestControllerTest {

    @InjectMocks
    RebelsRestController restController;

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
                        .latitude(2F)
                        .longitude(2F)
                        .build();

        rebelsCreatedResponse =
                RebelsCreatedResponse
                        .builder()
                        .rebel(1L)
                        .name("Marcos")
                        .age(28)
                        .genre("masculino")
                        .baseName("Vênus")
                        .latitude(2F)
                        .longitude(2F)
                        .build();

        rebel = Rebel
                .builder()
                .rebel(1L)
                .name("Marcos")
                .age(28)
                .genre("masculino")
                .baseName("Vênus")
                .latitude(2F)
                .longitude(2F)
                .build();

        rebelList = new ArrayList<>();
        rebelList.add(rebel);

        changeRebelsRequest = ChangeRebelsRequest
                .builder()
                .name("Marcos")
                .age(28)
                .genre("masculino")
                .baseName("Lua")
                .latitude(2F)
                .longitude(2F)
                .build();

        changeRebelResponse = ChangeRebelResponse
                .builder()
                .rebel(1L)
                .name("Marcos")
                .age(28)
                .genre("masculino")
                .baseName("Lua")
                .latitude(2F)
                .longitude(2F)
                .build();

        changeRebelsRequestPartially = ChangeRebelsRequest
                .builder()
                .age(40)
                .baseName("Marte")
                .build();

        partiallyChangedRebelResponse = ChangeRebelResponse
                .builder()
                .rebel(1L)
                .name("Marcos")
                .age(40)
                .genre("masculino")
                .baseName("Marte")
                .latitude(2F)
                .longitude(2F)
                .build();

        RebelsCreatedResponse teste = new RebelsCreatedResponse();
        BeanUtils.copyProperties(rebelsRequest, teste, "id");
    }

    @Test
    @DisplayName("Deve retorna uma resposta de rebelde,que deve ter id, nome, idade, gênero, nome da base, latitude, longitude, traidor e número de reportes.")
    void shouldCreateValidRebelResponseWithoutNullProperties() {
        Mockito.when(rebelsService.create(rebelsRequest)).thenReturn(rebelsCreatedResponse);

        RebelsCreatedResponse response = rebelsService.create(rebelsRequest);
        RebelsCreatedResponse teste = rebelsService.create(rebelsRequest);

        assertNotNull(response);
        assertNotNull(response.getRebel());

        System.out.println(teste);
        System.out.println(rebelsRequest);


        assertEquals(rebelsRequest.getName(),response.getName());
        assertEquals(teste.getName(),response.getName());
        assertEquals(teste,response);

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
        assertEquals(rebelChangedResponse.getLatitude(), changeRebelsRequest.getLatitude());
        assertEquals(rebelChangedResponse.getLongitude(), changeRebelsRequest.getLongitude());
    }

    @Test
    void shouldChangePartiallyTheRebel() {
        Mockito.when(rebelsService.changeParcialRebel(1L, changeRebelsRequestPartially)).thenReturn(partiallyChangedRebelResponse);

        ChangeRebelResponse rebel =  rebelsService.changeParcialRebel(1L, changeRebelsRequestPartially);
        assertNotNull(rebel);
    }
}