package br.com.letscode.starwars.service;

import br.com.letscode.starwars.enums.errors.RebelValidationError;
import br.com.letscode.starwars.exception.BusinessException;
import br.com.letscode.starwars.model.DTO.AcceptedNegotiationResponse;
import br.com.letscode.starwars.model.DTO.StartNegotiationRequest;
import br.com.letscode.starwars.model.DTO.StartedNegotiationResponse;
import br.com.letscode.starwars.model.Entity.Negotiation;
import br.com.letscode.starwars.repository.NegotiationRepository;
import br.com.letscode.starwars.repository.RebelsInventoryRepository;
import br.com.letscode.starwars.repository.RebelsRepository;
import br.com.letscode.starwars.repository.TraitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static br.com.letscode.starwars.enums.errors.NegotiationValidationError.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class NegotiationService {
    private final RebelsRepository rebelsRepository;
    private final NegotiationRepository negotiationRepository;
    private final RebelsInventoryRepository repositoryInventory;
    private final TraitorRepository traitorRepository;

    public List<Negotiation> getRebelNegotiations(Long rebelId){
        var rebelOptional = rebelsRepository.findById(rebelId);

        if(rebelOptional.isEmpty()){
            throw new BusinessException(RebelValidationError.NOT_FOUND_REBEL, "Rebelde não existe.");
        }

        return negotiationRepository.getRebelNegotiations(rebelId);
    }

    public StartedNegotiationResponse startNew(Long rebelId, StartNegotiationRequest request){
        log.debug("Received rebel to create: {}",request);

        var sellerRebelOptional = rebelsRepository.findById(rebelId);
        var buyerRebelOptional = rebelsRepository.findById(request.getBuyerRebel());

        if(sellerRebelOptional.isEmpty()){
            throw new BusinessException(REBEL_SELLER_NOT_FOUND, "Rebelde negociante não existe.");
        }

        var sellerRebel = sellerRebelOptional.get();

        if (traitorRepository.getTraitor(rebelId).isPresent()){
            throw new BusinessException(DEALER_WITH_LOCKED_INVENTORY, "Rebelde traidor não é digno de negociar (inventário bloqueado).");
        }

        if (rebelId.equals(request.getBuyerRebel())){
            throw new BusinessException(FORBIDDEN_NEGOTIATE_WITH_YOURSELF, "Um rebelde não pode negociar consigo mesmo.");
        }

        if(buyerRebelOptional.isEmpty()){
            throw new BusinessException(REBEL_BUYER_NOT_FOUND, "Rebelde cliente não existe.");
        }
        var buyerRebel = buyerRebelOptional.get();
        if (traitorRepository.getTraitor(request.getBuyerRebel()).isPresent()){
            throw new BusinessException(CLIENT_WITH_LOCKED_INVENTORY, "Rebelde cliente é um traidor! Melhor não negociar..");
        }

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
        System.out.println(newNegotiation);
        var savedNegotiation = negotiationRepository.save(newNegotiation);
        System.out.println(savedNegotiation);
        log.debug("Saved Negotiation => {}", savedNegotiation);
        return StartedNegotiationResponse.of(savedNegotiation);
    }

    @Transactional
    public AcceptedNegotiationResponse accept(Long rebelId, Long negotiationId) {
        log.debug("Received negotiation to accept: {}", "");
        var buyerRebelOptional = rebelsRepository.findById(rebelId);
        var negotiationOptional = negotiationRepository.findById(negotiationId);
        if(buyerRebelOptional.isEmpty()){
            throw new BusinessException(REBEL_BUYER_NOT_FOUND, "Rebelde cliente não existe.");
        }

        if(negotiationOptional.isEmpty()){
            throw new BusinessException(NEGOTIATION_NOT_FOUND, "Negociação não existe.");
        }

        if (traitorRepository.getTraitor(rebelId).isPresent()){
            throw new BusinessException(CLIENT_WITH_LOCKED_INVENTORY, "Agora por ser um traidor não poderás negociar..");
        }

        var negotiation = negotiationOptional.get();
        var sellerRebel = negotiation.getSellerRebel();
        var buyerRebel = negotiation.getBuyerRebel();

        if (traitorRepository.getTraitor(sellerRebel.getRebel()).isPresent()){
            throw new BusinessException(DEALER_WITH_LOCKED_INVENTORY, "Rebelde negociante virou um traidor! Melhor não negociar..");
        }

        var negotiationResponse = AcceptedNegotiationResponse.of(negotiation);
        var availableItems = negotiationResponse.getAvailableItems();
        var requiredItems = negotiationResponse.getRequiredItems();

        if(rebelId.equals(sellerRebel.getRebel())){
            throw new BusinessException(REBEL_IS_NOT_DEALER, "Não participação do rebelde cliente na negociação.");
        }
        if(!sellerRebel.hasItemsInInventory(availableItems)){
            throw new BusinessException(DEALER_WITH_INSUFFICIENT_ITEMS, "O rebelde negociante está oferecendo demais...");
        }

        if(!buyerRebel.hasItemsInInventory(requiredItems)){
            throw new BusinessException(CLIENT_WITH_INSUFFICIENT_ITEMS, "O rebelde cliente não possui todos estes items..");
        }
        var sellerInventory = sellerRebel.getInventory();
        var buyerInventory = buyerRebel.getInventory();

        sellerInventory.removeItems(availableItems);
        buyerInventory.addItems(availableItems);

        sellerInventory.addItems(requiredItems);
        buyerInventory.removeItems(requiredItems);

        repositoryInventory.save(sellerInventory);
        repositoryInventory.save(buyerInventory);
        negotiationRepository.delete(negotiation);

        log.debug("Received rebel in service: {}", "");
        return negotiationResponse;
    }

    public void refuse(Long rebelId, Long negotiationId) {
        log.debug("Received negotiation to accept: {}", "");

        var buyerRebelOptional = rebelsRepository.findById(rebelId);
        var negotiationOptional = negotiationRepository.findById(negotiationId);

        if(buyerRebelOptional.isEmpty()){
            throw new BusinessException(REBEL_BUYER_NOT_FOUND, "Rebelde cliente não existe.");
        }
        if(negotiationOptional.isEmpty()){
            throw new BusinessException(NEGOTIATION_NOT_FOUND, "Negociação não existe.");
        }
        var negotiation = negotiationOptional.get();
        var sellerRebel = negotiation.getSellerRebel();

        if(rebelId.equals(sellerRebel.getRebel())){
            throw new BusinessException(REBEL_IS_NOT_DEALER, "Não há participação do rebelde cliente na negociação.");
        }

        negotiationRepository.delete(negotiation);

        log.debug("Refused Negotiation");
    }
}