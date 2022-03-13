package br.com.letscode.starwars.service;

import br.com.letscode.starwars.model.DTO.*;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.repository.RebelsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RebelsService {

    private final RebelsRepository repository;

    public RebelsCreatedResponse create(CreateRebelsRequest request){
        log.debug("Received rebel to create: {}",request);
        Rebel newRebel = Rebel.of(request);
        Rebel savedRebel = repository.save(newRebel);
        log.debug("Received rebel in service: {}",savedRebel);
        return RebelsCreatedResponse.of(savedRebel);
    }

    public List<Rebel> getAllRebels(){
        List<Rebel> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    public Rebel findById(Long id) {
        return repository.findById(id).get();
    }

    public ChangeRebelResponse changeRebel(Long id, ChangeRebelsRequest request) {
        Rebel receiveRebel = Rebel.of(request);
        Rebel changeRebel = repository.findById(id).get();
        BeanUtils.copyProperties(receiveRebel, changeRebel, "rebel");
        changeRebel = repository.save(changeRebel);
        return ChangeRebelResponse.of(changeRebel);
    }

    public ChangeRebelResponse changeParcialRebel(Long id, ChangeRebelsRequest request) {
        Rebel receiveRebel = Rebel.of(request);
        Rebel changeRebel = repository.findById(id).get();
        String[] ignoreProperties = new String[9];
        ignoreProperties[0] = "id";
        ignoreProperties[1] = "traitor";
        ignoreProperties[2] = "reportsCounter";
        if(request.getName() == null){
            ignoreProperties[3] = "name";
        }
        if(request.getAge() == null){
            ignoreProperties[4] = "age";
        }
        if(request.getGenre() == null){
            ignoreProperties[5] = "genre";
        }
        if(request.getLatitud() == null){
            ignoreProperties[6] = "latitud";
        }
        if(request.getLongitud() == null){
            ignoreProperties[7] = "longitud";
        }
        if(request.getBaseName() == null){
            ignoreProperties[8] = "baseName";
        }

        BeanUtils.copyProperties(request, changeRebel, ignoreProperties);
        changeRebel = repository.save(changeRebel);
        return ChangeRebelResponse.of(changeRebel);
    }

}
