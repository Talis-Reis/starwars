package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.DTO.*;
import br.com.letscode.starwars.model.Entity.InventoryEntity;
import br.com.letscode.starwars.model.Entity.RebelEntity;
import br.com.letscode.starwars.service.RebelsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/rebels")
@AllArgsConstructor
@Slf4j
public class RebelsRestController {

    private final RebelsService rebelsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RebelsCreatedResponse createRebel(@RequestBody CreateRebelsRequest request){
        log.info("Creating rebel: {}", request);
        RebelsCreatedResponse rebel =  rebelsService.create(request);
        log.info("Rebel Created: {}", rebel);
        return rebel;
    }

    @PostMapping(value = "/inventory")
    @ResponseStatus(HttpStatus.CREATED)
    public RebelsInventoryCreatedResponse createInventory(@RequestBody CreateInventoryRebelRequest request){
        log.info("Creating inventory rebel: {}", request);
        RebelsInventoryCreatedResponse rebelInventory =  rebelsService.createInventory(request);
        log.info("Inventory Rebel Created: {}", rebelInventory);
        return rebelInventory;
    }

    @GetMapping
    public List<RebelEntity> getAll(){
        List<RebelEntity> retornoRebel = rebelsService.getAllRebels();
        return retornoRebel;
    }

    @GetMapping(value = "/{id}")
    public RebelEntity findById(@PathVariable("id") Long id){
        return rebelsService.findById(id);
    }

//    @GetMapping(value = "/inventary/{IdRebel}")
//    public InventoryEntity findByIdInventory(@PathVariable("IdRebel") Long IdRebel){
//        return rebelsService.findByIdInventory(IdRebel);
//    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChangeRebelResponse changeRebel (@PathVariable("id") Long id, @RequestBody @Valid ChangeRebelsRequest request){
        log.info("Editing Rebel: {}", request);
        ChangeRebelResponse rebel =  rebelsService.changeRebel(id, request);
        log.info("Rebel Edited: {}", rebel);
        return rebel;
    }
}
