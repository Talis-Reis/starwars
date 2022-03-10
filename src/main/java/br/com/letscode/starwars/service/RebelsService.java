package br.com.letscode.starwars.service;

import br.com.letscode.starwars.model.DTO.*;
import br.com.letscode.starwars.model.Entity.InventoryEntity;
import br.com.letscode.starwars.model.Entity.RebelEntity;
import br.com.letscode.starwars.repository.RebelsInventoryRepository;
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
    private final RebelsInventoryRepository repositoryInventory;

    public RebelsCreatedResponse create(CreateRebelsRequest request){
        log.debug("Received rebel to create: {}",request);
        RebelEntity newRebel = RebelEntity.of(request);
        RebelEntity savedRebel = repository.save(newRebel);
        log.debug("Received rebel in service: {}",savedRebel);
        return RebelsCreatedResponse.of(savedRebel);
    }

    public RebelsInventoryCreatedResponse createInventory( CreateInventoryRebelRequest request) {
        log.debug("Received inventory rebel to create: {}",request);
        InventoryEntity newInventoryRebel = InventoryEntity.of(request);
        InventoryEntity savedInventoryRebel = repositoryInventory.save(newInventoryRebel);
        log.debug("Received inventory rebel in service: {}",savedInventoryRebel);
        return RebelsInventoryCreatedResponse.of(savedInventoryRebel);
    }

    public List<RebelEntity> getAllRebels(){
        List<RebelEntity> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    public RebelEntity findById(Long id) {
        return repository.findById(id).get();
    }

    public ChangeRebelResponse changeRebel(Long id, ChangeRebelsRequest request) {
        RebelEntity receiveRebel = RebelEntity.of(request);
        RebelEntity changeRebel = repository.findById(id).get();
        BeanUtils.copyProperties(receiveRebel, changeRebel, "idRebel");
        changeRebel = repository.save(changeRebel);
        return ChangeRebelResponse.of(changeRebel);
    }

//    public InventoryEntity findByIdInventory(Long IdRebel) {
//        return repositoryInventory.findById(IdRebel).get();
//    }

}
