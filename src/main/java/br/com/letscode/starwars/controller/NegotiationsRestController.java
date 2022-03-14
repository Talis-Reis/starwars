package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.DTO.AcceptedNegotiationResponse;
import br.com.letscode.starwars.model.DTO.StartNegotiationRequest;
import br.com.letscode.starwars.model.DTO.StartedNegotiationResponse;
import br.com.letscode.starwars.service.NegotiationService;
import br.com.letscode.starwars.service.RebelsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rebels/{id}/negotiations")
@Tag(name="Negotiations")
@AllArgsConstructor
@Slf4j
public class NegotiationsRestController {
    private final RebelsService rebelsService;
    private final NegotiationService negotiationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StartedNegotiationResponse createNegotiation(@PathVariable("id") Long id, @RequestBody StartNegotiationRequest request){
        log.info("Creating  negotiation: {}", request);
        var negotiation =  negotiationService.startNew(id, request);
        log.info("Negotiation Created: {}", negotiation);
        return negotiation;
    }

    @PatchMapping(value = "/{negotiationId}/accept")
    public AcceptedNegotiationResponse acceptNegotiation(@PathVariable("id") Long id, @PathVariable("negotiationId")Long negotiationId){
        log.info("Accept  negotiation: {}", negotiationId);
        var negotiation =  negotiationService.accept(id, negotiationId);
        log.info("Negotiation Accepted: {}", negotiation);
        return negotiation;
    }
}
