package br.com.letscode.starwars.service;

import br.com.letscode.starwars.enums.errors.NegotiationValidationError;
import br.com.letscode.starwars.enums.errors.RebelValidationError;
import br.com.letscode.starwars.exception.BusinessException;
import br.com.letscode.starwars.model.DTO.AcceptedNegotiationResponse;
import br.com.letscode.starwars.model.DTO.InventoryEmbedded;
import br.com.letscode.starwars.model.DTO.StartNegotiationRequest;
import br.com.letscode.starwars.model.DTO.StartedNegotiationResponse;
import br.com.letscode.starwars.model.Entity.Inventory;
import br.com.letscode.starwars.model.Entity.Negotiation;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.model.Entity.Traitor;
import br.com.letscode.starwars.repository.NegotiationRepository;
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
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class NegotiationServiceTest {
    @InjectMocks
    NegotiationService negotiationService;

    @Mock
    RebelsRepository rebelsRepository;
    @Mock
    NegotiationRepository negotiationRepository;
    @Mock
    RebelsInventoryRepository repositoryInventory;
    @Mock
    TraitorRepository traitorRepository;

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
    Negotiation negotiationSaved;
    @Mock
    Rebel sellerRebel;
    @Mock
    Rebel buyerRebel;


    @BeforeEach
    void setup() {
        availableItems = InventoryEmbedded
                .builder()
                .ammunition(0)
                .waters(0)
                .weapons(0)
                .food(2)
                .build();

        requiredItems = InventoryEmbedded
                .builder()
                .ammunition(0)
                .waters(1)
                .weapons(0)
                .food(0)
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
                .inventory(Inventory.of(availableItems))
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
                .inventory(Inventory.of(requiredItems))
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
        acceptedNegotiationResponse.setSellerRebel(sellerRebel.getRebel());

        negotiation = Negotiation.of(sellerRebel, buyerRebel, startNegotiationRequest);

        negotiationSaved = new Negotiation();
        BeanUtils.copyProperties(negotiation, negotiationSaved);
        negotiationSaved.setNegotiation(1L);

        negotiationList = new ArrayList<>();
        negotiationList.add(negotiationSaved);


        Mockito.when(rebelsRepository.findById(1L)).thenReturn(Optional.of(sellerRebel));
        Mockito.when(rebelsRepository.findById(2L)).thenReturn(Optional.of(buyerRebel));
        Mockito.when(traitorRepository.getTraitor(1L)).thenReturn(Optional.empty());
        Mockito.when(traitorRepository.getTraitor(2L)).thenReturn(Optional.empty());

        Mockito.when(negotiationRepository.findById(negotiationSaved.getNegotiation())).thenReturn(Optional.of(negotiationSaved));
        Mockito.when(negotiationRepository.save(negotiation)).thenReturn(negotiationSaved);
        Mockito.when(negotiationRepository.getRebelNegotiations(sellerRebel.getRebel())).thenReturn(negotiationList);

    }

    @Test
    @DisplayName("Deve validar se o retorno de negociação iniciada por um rebelde contém os dados de requisição.")
    void shouldStartNegotiationWithoutNullProperties() {
        var response = negotiationService.startNew(sellerRebel.getRebel(), startNegotiationRequest);

        assertNotNull(response);
        assertEquals(response.getNegotiation(), 1L);
        assertEquals(response.getAvailableItems().getAmmunition(), startNegotiationRequest.getAvailableItems().getAmmunition());
    }

    @Test
    @DisplayName("Deve lançar um erro de negociador não encontrado.")
    void shouldNotFindSellerInStartNegotiation() {
        Mockito.when(rebelsRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.startNew(sellerRebel.getRebel(), startNegotiationRequest);        });
        assertEquals(exception.getType(), NegotiationValidationError.REBEL_SELLER_NOT_FOUND);
    }

    @Test
    @DisplayName("Deve lançar um erro de comprador não encontrado.")
    void shouldNotFindBuyerInStartNegotiation() {
        Mockito.when(rebelsRepository.findById(2L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.startNew(sellerRebel.getRebel(), startNegotiationRequest);        });
        assertEquals(exception.getType(), NegotiationValidationError.REBEL_BUYER_NOT_FOUND);
    }

    @Test
    @DisplayName("Deve lançar um erro de negociador com items insuficientes.")
    void shouldDealerWithInsufficientItemsInStartNegotiation() {
        sellerRebel.getInventory().setFood(1);
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.startNew(sellerRebel.getRebel(), startNegotiationRequest);        });
        assertEquals(exception.getType(), NegotiationValidationError.DEALER_WITH_INSUFFICIENT_ITEMS);
    }

    @Test
    @DisplayName("Deve lançar um erro de comprador com items insuficientes.")
    void shouldClientWithInsufficientItemsInStartNegotiation() {
        buyerRebel.getInventory().setWaters(0);
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.startNew(sellerRebel.getRebel(), startNegotiationRequest);        });
        assertEquals(exception.getType(), NegotiationValidationError.CLIENT_WITH_INSUFFICIENT_ITEMS);
    }

    @Test
    @DisplayName("Deve lançar um erro de auto negociação não permitada.")
    void shouldThrowErrorAtNegotiateWithYourselfInStartNegotiation() {
        startNegotiationRequest.setBuyerRebel(sellerRebel.getRebel());
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.startNew(sellerRebel.getRebel(), startNegotiationRequest);        });
        assertEquals(exception.getType(), NegotiationValidationError.FORBIDDEN_NEGOTIATE_WITH_YOURSELF);
    }

    @Test
    @DisplayName("Deve lançar um erro de negociação injusta.")
    void shouldThrowErrorAtUnfairNegotiationInStartNegotiation() {
        buyerRebel.getInventory().setFood(1);
        requiredItems.setFood(1);
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.startNew(sellerRebel.getRebel(), startNegotiationRequest);        });
        assertEquals(exception.getType(), NegotiationValidationError.UNFAIR_NEGOTIATION);
    }

    @Test
    @DisplayName("Deve lançar um erro de negociador traidor.")
    void shouldThrowSellerTraitorInStartNegotiation() {
        Mockito.when(traitorRepository.getTraitor(sellerRebel.getRebel())).thenReturn(Optional.of(new Traitor()));
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.startNew(sellerRebel.getRebel(), startNegotiationRequest);        });
        assertEquals(exception.getType(), NegotiationValidationError.DEALER_WITH_LOCKED_INVENTORY);
    }

    @Test
    @DisplayName("Deve lançar um erro de comprador traidor.")
    void shouldThrowClientTraitorInStartNegotiation() {
        Mockito.when(traitorRepository.getTraitor(buyerRebel.getRebel())).thenReturn(Optional.of(new Traitor()));
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.startNew(sellerRebel.getRebel(), startNegotiationRequest);        });
        assertEquals(exception.getType(), NegotiationValidationError.CLIENT_WITH_LOCKED_INVENTORY);
    }

    @Test
    @DisplayName("Deve validar se retorno de todas as regociações de um rebelde.")
    void shouldListAllNegotiationsWithoutEmptyData() {
        var response = negotiationService.getRebelNegotiations(sellerRebel.getRebel());;

        assertNotNull(response);
        assertEquals(response, negotiationList);
    }

    @Test
    @DisplayName("Deve lançar um erro de rebelde não encontrado.")
    void shouldNotFindRebelInListNegotiations() {
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.getRebelNegotiations(3L);
        });
        assertEquals(exception.getType(), RebelValidationError.NOT_FOUND_REBEL);
    }

    @Test
    @DisplayName("Deve validar se retorno de aceitação de uma negociação.")
    void shouldAllowAcceptNegotiation() {
        var response = negotiationService.accept(buyerRebel.getRebel(), negotiationSaved.getNegotiation());
        assertEquals(response, acceptedNegotiationResponse);
    }

    @Test
    @DisplayName("Deve lançar um erro de comprador não encontrado.")
    void shouldNotFindBuyerInAcceptNegotiation() {
        Mockito.when(rebelsRepository.findById(2L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.accept(buyerRebel.getRebel(), negotiation.getNegotiation());;
        });
        assertEquals(exception.getType(), NegotiationValidationError.REBEL_BUYER_NOT_FOUND);
    }

    @Test
    @DisplayName("Deve lançar um erro de negociação não encontrada.")
    void shouldNotFindNegotiationInAcceptNegotiation() {
        Mockito.when(negotiationRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.accept(buyerRebel.getRebel(), negotiationSaved.getNegotiation());
        });
        assertEquals(exception.getType(), NegotiationValidationError.NEGOTIATION_NOT_FOUND);
    }

    @Test
    @DisplayName("Deve lançar um erro de negociador com items insuficientes.")
    void shouldDealerWithInsufficientItemsAcceptNegotiation() {
        sellerRebel.getInventory().setFood(1);
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.accept(buyerRebel.getRebel(), negotiationSaved.getNegotiation());
        });
        assertEquals(exception.getType(), NegotiationValidationError.DEALER_WITH_INSUFFICIENT_ITEMS);
    }

    @Test
    @DisplayName("Deve lançar um erro de comprador com items insuficientes.")
    void shouldClientWithInsufficientItemsAcceptNegotiation() {
        buyerRebel.getInventory().setWaters(0);
        BusinessException exception = assertThrows(BusinessException.class, () -> {
                    negotiationService.accept(buyerRebel.getRebel(), negotiationSaved.getNegotiation());
                });
        assertEquals(exception.getType(), NegotiationValidationError.CLIENT_WITH_INSUFFICIENT_ITEMS);
    }

    @Test
    @DisplayName("Deve lançar um erro de negociador traidor.")
    void shouldThrowSellerTraitorInAcceptNegotiation() {
        Mockito.when(traitorRepository.getTraitor(sellerRebel.getRebel())).thenReturn(Optional.of(new Traitor()));
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.accept(buyerRebel.getRebel(), negotiationSaved.getNegotiation());
        });
        assertEquals(exception.getType(), NegotiationValidationError.DEALER_WITH_LOCKED_INVENTORY);
    }

    @Test
    @DisplayName("Deve lançar um erro de comprador traidor.")
    void shouldThrowClientTraitorInAcceptNegotiation() {
        Mockito.when(traitorRepository.getTraitor(buyerRebel.getRebel())).thenReturn(Optional.of(new Traitor()));
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.accept(buyerRebel.getRebel(), negotiationSaved.getNegotiation());
        });
        assertEquals(exception.getType(), NegotiationValidationError.CLIENT_WITH_LOCKED_INVENTORY);
    }

    @Test
    @DisplayName("Deve lançar um erro se o rebelde não for o cliente ao aceitar uma negociação.")
    void shouldNotRebelIsNotTheBuyerInAcceptNegotiation() {

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.accept(sellerRebel.getRebel(), negotiationSaved.getNegotiation());;
        });
        assertEquals(exception.getType(), NegotiationValidationError.REBEL_IS_NOT_DEALER);
    }

    @Test
    @DisplayName("Deve validar se retorno de recusação de uma negociação.")
    void shouldAllowRefuseNegotiation() {
        negotiationService.refuse(buyerRebel.getRebel(), negotiationSaved.getNegotiation());
    }

    @Test
    @DisplayName("Deve lançar um erro de comprador não encontrado.")
    void shouldNotFindBuyerInRefuseNegotiation() {
        Mockito.when(rebelsRepository.findById(2L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.refuse(buyerRebel.getRebel(), negotiation.getNegotiation());;
        });
        assertEquals(exception.getType(), NegotiationValidationError.REBEL_BUYER_NOT_FOUND);
    }

    @Test
    @DisplayName("Deve lançar um erro de negociação não encontrada.")
    void shouldNotFindNegotiationInRefuseNegotiation() {
        Mockito.when(negotiationRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.refuse(buyerRebel.getRebel(), negotiationSaved.getNegotiation());;
        });
        assertEquals(exception.getType(), NegotiationValidationError.NEGOTIATION_NOT_FOUND);
    }

    @Test
    @DisplayName("Deve lançar um erro se o rebelde não for o cliente ao recusar uma negociação.")
    void shouldNotRebelIsNotTheBuyerInRefuseNegotiation() {

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            negotiationService.refuse(sellerRebel.getRebel(), negotiationSaved.getNegotiation());;
        });
        assertEquals(exception.getType(), NegotiationValidationError.REBEL_IS_NOT_DEALER);
    }
}