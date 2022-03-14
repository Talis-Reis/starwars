package br.com.letscode.starwars.service;

import br.com.letscode.starwars.exception.BusinessException;
import br.com.letscode.starwars.model.DTO.AcceptedNegotiationResponse;
import br.com.letscode.starwars.model.DTO.StartNegotiationRequest;
import br.com.letscode.starwars.model.DTO.StartedNegotiationResponse;
import br.com.letscode.starwars.model.Entity.Negotiation;
import br.com.letscode.starwars.repository.NegotiationRepository;
import br.com.letscode.starwars.repository.RebelsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static br.com.letscode.starwars.enums.errors.NegotiationValidationError.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class NegotiationService {
    private final RebelsRepository rebelsRepository;
    private final NegotiationRepository negotiationRepository;

    @Transactional
    public StartedNegotiationResponse startNew(Long rebelId, StartNegotiationRequest request){
        log.debug("Received rebel to create: {}",request);
        log.debug("Received rebel in service: {}", "");

        var sellerRebelOptional = rebelsRepository.findById(rebelId);
        var buyerRebelOptional = rebelsRepository.findById(request.getBuyerRebel());

        if(sellerRebelOptional.isEmpty()){
            throw new BusinessException(REBEL_SELLER_NOT_FOUND, "Rebelde negociante não existe.");
        }
        var sellerRebel = sellerRebelOptional.get();

//        if (sellerRebel.getTraitor() == 1){
//            throw new BusinessException(DEALER_WITH_LOCKED_INVENTORY, "Rebelde traidor não é digno de negociar (inventário bloqueado).");
//        }

        if (rebelId.equals(request.getBuyerRebel())){
            throw new BusinessException(FORBIDDEN_NEGOTIATE_WITH_YOURSELF, "Um rebelde não pode negociar consigo mesmo.");
        }

        if(buyerRebelOptional.isEmpty()){
            throw new BusinessException(REBEL_BUYER_NOT_FOUND, "Rebelde cliente não existe.");
        }
        var buyerRebel = buyerRebelOptional.get();
//        if (buyerRebel.getTraitor() == 1){
//            throw new BusinessException(CLIENT_WITH_LOCKED_INVENTORY, "Rebelde cliente é um traidor! Melhor não negociar..");
//        }

        Negotiation newNegotiation = Negotiation.of(sellerRebel, buyerRebel, request);

        if(!sellerRebel.hasItemsInInventory(request.getAvailableItems())){
            throw new BusinessException(DEALER_WITH_INSUFFICIENT_ITEMS, "O rebelde negociante está oferecendo demais...");
        }

        if(!buyerRebel.hasItemsInInventory(request.getRequiredItems())){
            throw new BusinessException(CLIENT_WITH_INSUFFICIENT_ITEMS, "O rebelde cliente não possui todos estes items..");
        }

        if(!newNegotiation.isFair()){
            throw new BusinessException(UNFAIR_NEGOTIATION, "A quantidade pontos de ambos os lados deve ser igual.");
        }

        log.debug("Negotiation => {}", newNegotiation);
        var savedNegotiation = negotiationRepository.save(newNegotiation);
        return StartedNegotiationResponse.of(savedNegotiation);
    }

    @Transactional
    public AcceptedNegotiationResponse accept(Long rebelId, Long negotiationId) {
        log.debug("Received rebel to create: {}", "");
        log.debug("Received rebel in service: {}", "");
        return new AcceptedNegotiationResponse();
    }
}
