package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.DTO.ChangeRebelResponse;
import br.com.letscode.starwars.model.DTO.ChangeRebelsRequest;
import br.com.letscode.starwars.model.DTO.CreateRebelsRequest;
import br.com.letscode.starwars.model.DTO.RebelsCreatedResponse;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.service.RebelsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/rebel")
@Tag(name="Rebels")
@AllArgsConstructor
@Slf4j
public class RebelsRestController {

    private final RebelsService rebelsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RebelsCreatedResponse createRebel(@RequestBody @Valid CreateRebelsRequest request){
        log.info("Creating rebel: {}", request);
        RebelsCreatedResponse rebel =  rebelsService.create(request);
        log.info("Rebel Created: {}", rebel);
        return rebel;
    }

    @GetMapping
    public List<Rebel> getAll(){
        List<Rebel> rebels = rebelsService.getAllRebels();
        return rebels;
    }

    @GetMapping(value = "/{id}")
    public Rebel findById(@PathVariable("id") Long id){
        return rebelsService.findById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChangeRebelResponse changeRebel (@PathVariable("id") Long id, @RequestBody @Valid ChangeRebelsRequest request){
        log.info("Editing Rebel: {}", request);
        ChangeRebelResponse rebel =  rebelsService.changeRebel(id, request);
        log.info("Rebel Edited: {}", rebel);
        return rebel;
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChangeRebelResponse changeParcialRebel (@PathVariable("id") Long id,@RequestBody @Valid ChangeRebelsRequest request){
        log.info("Editing Partial Rebel: {}", request);
        ChangeRebelResponse rebel =  rebelsService.changePartialRebel(id, request);
        log.info("Rebel Edited Partial: {}", rebel);
        return rebel;
    }

}
