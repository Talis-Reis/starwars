package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.CreateRebelsRequest;
import br.com.letscode.starwars.model.Rebels;
import br.com.letscode.starwars.model.RebelsCreatedResponse;
import br.com.letscode.starwars.service.RebelsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rebels")
@AllArgsConstructor
@Slf4j
public class RebelsRestController {

    private final RebelsService rebelsService;

    @PostMapping
    public RebelsCreatedResponse createBook(@RequestBody CreateRebelsRequest request){
        log.info("Creating book: {}", request);
        RebelsCreatedResponse book =  rebelsService.create(request);
        log.info("Book Created: {}",request);
        return book;
    }

    @GetMapping
    public List<Rebels> getAll(){
        return rebelsService.getAllBooks();
    }

}
