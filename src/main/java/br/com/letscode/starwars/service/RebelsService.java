package br.com.letscode.starwars.service;

import br.com.letscode.starwars.model.CreateRebelsRequest;
import br.com.letscode.starwars.model.Rebels;
import br.com.letscode.starwars.model.RebelsCreatedResponse;
import br.com.letscode.starwars.repository.RebelsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RebelsService {

    private final RebelsRepository repository;

    public RebelsCreatedResponse create(CreateRebelsRequest request){
        log.debug("Received book to create: {}",request);
        Rebels newBook = Rebels.of(request);
        Rebels savedBook = repository.save(newBook);
        log.debug("Received book in service: {}",savedBook);
        return RebelsCreatedResponse.of(savedBook);
    }

    public List<Rebels> getAllBooks(){
        List<Rebels> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }
}
