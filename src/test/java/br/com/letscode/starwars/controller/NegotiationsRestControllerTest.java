package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.DTO.AcceptedNegotiationResponse;
import br.com.letscode.starwars.model.DTO.InventoryEmbedded;
import br.com.letscode.starwars.model.DTO.StartNegotiationRequest;
import br.com.letscode.starwars.model.DTO.StartedNegotiationResponse;
import br.com.letscode.starwars.model.Entity.Negotiation;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.service.NegotiationService;
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
class NegotiationsRestControllerTest {
    @InjectMocks
    NegotiationsRestController restController;

    @Mock
    NegotiationService negotiationService;
    @Mock
    StartedNegotiationResponse startedNegotiationResponse;
    @Mock
    StartNegotiationRequest startNegotiationRequest;
    @Mock
    AcceptedNegotiationResponse acceptedNegotiationResponse;
    @Mock
    InventoryEmbedded availableItems;
    @Mock
    InventoryEmbedded requiredItems;

    @Mock
    List<Negotiation> negotiationList;
    @Mock
    Negotiation negotiation;
    @Mock
    Rebel sellerRebel;
    @Mock
    Rebel buyerRebel;


    @BeforeEach
    void setup() {
        availableItems = InventoryEmbedded
                .builder()
                .food(2)
                .build();

        availableItems = InventoryEmbedded
                .builder()
                .waters(1)
                .build();

        sellerRebel = Rebel.
                builder()
                .rebel(1L)
                .name("Maicon")
                .age(25)
                .genre("masculino")
                .baseName("Urano")
                .latitude(5F)
                .longitude(7F)
                .build();

        buyerRebel = Rebel.
                builder()
                .rebel(2L)
                .name("Marcos")
                .age(28)
                .genre("masculino")
                .baseName("Vênus")
                .latitude(2F)
                .longitude(2F)
                .build();

        startNegotiationRequest =
                StartNegotiationRequest
                        .builder()
                        .buyerRebel(buyerRebel.getRebel())
                        .requiredItems(requiredItems)
                        .availableItems(availableItems)
                        .build();

        startedNegotiationResponse = new StartedNegotiationResponse();
        BeanUtils.copyProperties(startNegotiationRequest, startedNegotiationResponse);
        startedNegotiationResponse.setNegotiation(1L);

        acceptedNegotiationResponse = new AcceptedNegotiationResponse();
        BeanUtils.copyProperties(startedNegotiationResponse, acceptedNegotiationResponse);


        negotiation = Negotiation.of(sellerRebel,buyerRebel, startNegotiationRequest);
        negotiationList = new ArrayList<>();
        negotiationList.add(negotiation);

    }

    @Test
    @DisplayName("Deve validar se o retorno de negociação iniciada por um rebelde contém os dados de requisição.")
    void shouldStartNegotiationWithoutNullProperties() {
        Mockito.when(negotiationService.startNew(sellerRebel.getRebel(), startNegotiationRequest)).thenReturn(startedNegotiationResponse);

        var response = restController.createNegotiation(sellerRebel.getRebel(), startNegotiationRequest);;

        assertNotNull(response);
        assertEquals(response.getNegotiation(), 1L);
        assertEquals(response.getAvailableItems().getAmmunition(), startNegotiationRequest.getAvailableItems().getAmmunition());
    }

    @Test
    @DisplayName("Deve validar se retorno de todas as regociações de um rebelde.")
    void shouldListAllNegotiationsWithoutEmptyData() {
        Mockito.when(negotiationService.getRebelNegotiations(sellerRebel.getRebel())).thenReturn(negotiationList);

        var response = restController.getAllRebelNegotiations(sellerRebel.getRebel());;

        assertNotNull(response);
        assertEquals(response.size(), 1);
    }

    @Test
    @DisplayName("Deve validar se retorno de aceitação de uma negociação.")
    void shouldAllowAcceptNegotiation() {
        Mockito.when(negotiationService.accept(sellerRebel.getRebel(), negotiation.getNegotiation())).thenReturn(acceptedNegotiationResponse);

        var response = restController.acceptNegotiation(sellerRebel.getRebel(), negotiation.getNegotiation());

        assertNotNull(response);
        assertEquals(response, acceptedNegotiationResponse);
    }

    @Test
    @DisplayName("Deve validar se retorno de recusação de uma negociação.")
    void shouldAllowRefuseNegotiation() {
        restController.refuseNegotiation(sellerRebel.getRebel(), negotiation.getNegotiation());
    }
}