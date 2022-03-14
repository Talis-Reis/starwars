package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.DTO.StartNegotiationRequest;
import br.com.letscode.starwars.model.DTO.StartedNegotiationResponse;
import br.com.letscode.starwars.service.NegotiationService;
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
    private final NegotiationService negotiationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StartedNegotiationResponse createNegotiation(@PathVariable("id") Long id, @RequestBody StartNegotiationRequest request){
        log.info("Creating  negotiation: {}", request);
        var negotiation =  negotiationService.startNew(id, request);
        log.info("Negotiation Created: {}", negotiation);
        return negotiation;
    }
}
