package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.DTO.AcceptedNegotiationResponse;
import br.com.letscode.starwars.model.DTO.StartNegotiationRequest;
import br.com.letscode.starwars.model.DTO.StartedNegotiationResponse;
import br.com.letscode.starwars.model.Entity.Negotiation;
import br.com.letscode.starwars.service.NegotiationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/rebel/{buyerId}/negotiations")
@Tag(name="Negotiations")
@AllArgsConstructor
@Slf4j
public class NegotiationsRestController {
    private final NegotiationService negotiationService;

    @GetMapping
    public ArrayList<Negotiation> getAllRebelNegotiations(@PathVariable("buyerId") Long buyerId){
        return (ArrayList<Negotiation>) negotiationService.getRebelNegotiations(buyerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StartedNegotiationResponse createNegotiation(@PathVariable("buyerId") Long buyerId, @RequestBody StartNegotiationRequest request){
        log.info("Creating  negotiation: {}", request);
        var negotiation =  negotiationService.startNew(buyerId, request);
        log.info("Negotiation Created: {}", negotiation);
        return negotiation;
    }

    @PatchMapping(value = "/{negotiationId}/accept")
    public AcceptedNegotiationResponse acceptNegotiation(@PathVariable("buyerId") Long buyerId, @PathVariable("negotiationId")Long negotiationId){
        log.info("Accept  negotiation: {}", negotiationId);
        var negotiation =  negotiationService.accept(buyerId, negotiationId);
        log.info("Negotiation Accepted: {}", negotiation);
        return negotiation;
    }

    @PatchMapping(value = "/{negotiationId}/refuse")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refuseNegotiation(@PathVariable("buyerId") Long buyerId, @PathVariable("negotiationId")Long negotiationId){
        log.info("Refuse  negotiation: {}", negotiationId);
        negotiationService.refuse(buyerId, negotiationId);
        log.info("Negotiation Refused: {}", negotiationId);
    }
}
