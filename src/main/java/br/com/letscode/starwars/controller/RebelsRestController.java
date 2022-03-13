package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.DTO.*;
import br.com.letscode.starwars.model.Entity.Inventory;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.service.PunctuationService;
import br.com.letscode.starwars.service.RebelsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rebels")
@Tag(name="Rebels")
@AllArgsConstructor
@Slf4j
public class RebelsRestController {

    private final RebelsService rebelsService;
    private final PunctuationService punctuationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RebelsCreatedResponse createRebel(@RequestBody CreateRebelsRequest request){
        log.info("Creating rebel: {}", request);
        RebelsCreatedResponse rebel =  rebelsService.create(request);
        punctuationService.createPunctuation();
        log.info("Rebel Created: {}", rebel);
        return rebel;
    }

    @GetMapping
    public List<Rebel> getAll(){
        List<Rebel> retornoRebel = rebelsService.getAllRebels();
        return retornoRebel;
    }

    @GetMapping(value = "/{id}")
    public Rebel findById(@PathVariable("id") Long id){
        return rebelsService.findById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChangeRebelResponse changeRebel (@PathVariable("id") Long id, @RequestBody ChangeRebelsRequest request){
        log.info("Editing Rebel: {}", request);
        ChangeRebelResponse rebel =  rebelsService.changeRebel(id, request);
        log.info("Rebel Edited: {}", rebel);
        return rebel;
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChangeRebelResponse changeParcialRebel (@PathVariable("id") Long id,@RequestBody ChangeRebelsRequest request){
        log.info("Editing Partial Rebel: {}", request);
        ChangeRebelResponse rebel =  rebelsService.changeParcialRebel(id, request);
        log.info("Rebel Edited Partial: {}", rebel);
        return rebel;
    }

}
