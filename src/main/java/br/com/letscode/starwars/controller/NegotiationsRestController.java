package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.DTO.AcceptedNegotiationResponse;
import br.com.letscode.starwars.model.DTO.ListNegotiationResponse;
import br.com.letscode.starwars.model.DTO.StartNegotiationRequest;
import br.com.letscode.starwars.model.DTO.StartedNegotiationResponse;
import br.com.letscode.starwars.model.Entity.Negotiation;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.service.NegotiationService;
import br.com.letscode.starwars.service.RebelsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rebels/{sellerId}/negotiations")
@Tag(name="Negotiations")
@AllArgsConstructor
@Slf4j
public class NegotiationsRestController {
    private final RebelsService rebelsService;
    private final NegotiationService negotiationService;

    @GetMapping
    public ArrayList<Negotiation> getAllRebelNegotiations(@PathVariable("sellerId") Long sellerId){
        return (ArrayList<Negotiation>) negotiationService.getRebelNegotiations(sellerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StartedNegotiationResponse createNegotiation(@PathVariable("sellerId") Long sellerId, @RequestBody StartNegotiationRequest request){
        log.info("Creating  negotiation: {}", request);
        var negotiation =  negotiationService.startNew(sellerId, request);
        log.info("Negotiation Created: {}", negotiation);
        return negotiation;
    }

    @PatchMapping(value = "/{negotiationId}/accept")
    public AcceptedNegotiationResponse acceptNegotiation(@PathVariable("sellerId") Long sellerId, @PathVariable("negotiationId")Long negotiationId){
        log.info("Accept  negotiation: {}", negotiationId);
        var negotiation =  negotiationService.accept(sellerId, negotiationId);
        log.info("Negotiation Accepted: {}", negotiation);
        return negotiation;
    }

    @PatchMapping(value = "/{negotiationId}/refuse")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refuseNegotiation(@PathVariable("sellerId") Long sellerId, @PathVariable("negotiationId")Long negotiationId){
        log.info("Refuse  negotiation: {}", negotiationId);
        negotiationService.refuse(sellerId, negotiationId);
        log.info("Negotiation Refused: {}", negotiationId);
    }
}
