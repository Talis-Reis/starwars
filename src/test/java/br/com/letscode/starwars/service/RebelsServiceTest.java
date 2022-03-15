package br.com.letscode.starwars.service;

import br.com.letscode.starwars.controller.NegotiationsRestController;
import br.com.letscode.starwars.model.DTO.*;
import br.com.letscode.starwars.model.Entity.Inventory;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.repository.RebelsInventoryRepository;
import br.com.letscode.starwars.repository.RebelsRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class RebelsServiceTest {
    @InjectMocks
    RebelsService rebelsService;

    @Mock
    RebelsRepository rebelRepository;
    @Mock
    RebelsInventoryRepository repositoryInventory;
    @Mock
    RebelsCreatedResponse rebelsCreatedResponse;
    @Mock
    CreateRebelsRequest rebelsRequest;

    @Mock
    List<Rebel> rebelList;
    @Mock
    Rebel rebel;
    @Mock
    Rebel savedRebel;

    @Mock
    InventoryEmbedded inventoryEmbedded;
    @Mock
    Inventory inventory;
    @Mock
    Inventory savedInventory;

    @Mock
    ChangeRebelsRequest changeRebelsRequest;
    @Mock
    ChangeRebelsRequest changeRebelsRequestPartially;

    @Mock
    ChangeRebelResponse changeRebelResponse;
    @Mock
    ChangeRebelResponse partiallyChangedRebelResponse;

    @BeforeEach
    void setUp() {
        inventoryEmbedded = InventoryEmbedded
                .builder()
                .ammunition(1)
                .food(3)
                .build();

        inventory = Inventory.of(inventoryEmbedded);

        savedInventory = new Inventory();
        BeanUtils.copyProperties(inventory, savedInventory);
        savedInventory.setRebel(rebel);
        savedInventory.setInventory(1L);

        rebelsRequest =
                CreateRebelsRequest
                        .builder()
                        .name("Marcos")
                        .age(28)
                        .genre("masculino")
                        .baseName("Vênus")
                        .latitude(2F)
                        .longitude(2F)
                        .inventory(inventoryEmbedded)
                        .build();

        rebelsCreatedResponse = new RebelsCreatedResponse();
        BeanUtils.copyProperties(rebelsRequest, rebelsCreatedResponse);
        rebelsCreatedResponse.setRebel(1L);

        rebel = Rebel.of(rebelsRequest);
        rebel.setInventory(savedInventory);


        savedRebel = new Rebel();
        BeanUtils.copyProperties(rebel, savedRebel);
        savedRebel.setRebel(1L);

        rebelList = new ArrayList<>();
        rebelList.add(rebel);

        changeRebelsRequest = new ChangeRebelsRequest();
        BeanUtils.copyProperties(rebelsRequest, changeRebelsRequest);
        changeRebelsRequest.setAge(22);

        changeRebelResponse = new ChangeRebelResponse();
        BeanUtils.copyProperties(changeRebelsRequest, changeRebelResponse);
        changeRebelResponse.setRebel(1L);

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

        RebelsCreatedResponse rebelInjetado = new RebelsCreatedResponse();
        BeanUtils.copyProperties(rebel, rebelInjetado, "id");
    }

    @Test
    @DisplayName("Deve retorna uma resposta de rebelde,que deve ter id, nome, idade, gênero, nome da base, latitude, longitude, traidor e número de reportes.")
    void shouldCreateValidRebelResponseWithoutNullProperties() {
        Mockito.when(rebelRepository.save(rebel)).thenReturn(savedRebel);
        Mockito.when(rebelRepository.save(savedRebel)).thenReturn(savedRebel);
        Mockito.when(repositoryInventory.save(inventory)).thenReturn(savedInventory);


        RebelsCreatedResponse response = rebelsService.create(rebelsRequest);;

        assertNotNull(response);
        assertEquals(rebelsCreatedResponse, response);

    }

    @Test
    @DisplayName("Deve retornar todos os rebeldes salvos em um repositório.")
    void shouldGetAllRebelsInRepository() {
        Mockito.when(rebelRepository.findAll()).thenReturn(rebelList);
        List<Rebel> returnRebel = rebelsService.getAllRebels();

        assertEquals(returnRebel, rebelList);
    }

    @Test
    @DisplayName("Deve retornar um rebelde a partir de seu id.")
    void shouldFindRebelById() {
        Mockito.when(rebelRepository.findById(1L)).thenReturn(Optional.of(rebel));

        Rebel foundRebelById = rebelsService.findById(1L);

        assertNotNull(foundRebelById);
        assertEquals(foundRebelById, rebel);
    }

    @Test
    @DisplayName("A resposta do rebelde modificado deve ter as mesmas propriedades do rebelde que contém as propriedades requisitadas para serem modificadas.")
    void shouldChangeRebel() {
        Mockito.when(rebelRepository.findById(1L)).thenReturn(Optional.of(rebel));
        Mockito.when(rebelRepository.save(rebel)).thenReturn(rebel);

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
        Mockito.when(rebelRepository.findById(1L)).thenReturn(Optional.of(rebel));
        Mockito.when(rebelRepository.save(rebel)).thenReturn(rebel);

        ChangeRebelResponse rebel =  rebelsService.changeParcialRebel(1L, changeRebelsRequestPartially);
        assertNotNull(rebel);
    }

    @Test
    void shouldChangePartiallyTheRebelWithAllNull() {
        Mockito.when(rebelRepository.findById(1L)).thenReturn(Optional.of(rebel));
        Mockito.when(rebelRepository.save(rebel)).thenReturn(rebel);

        ChangeRebelResponse rebel =  rebelsService.changeParcialRebel(1L, new ChangeRebelsRequest());
        assertNotNull(rebel);
    }


}