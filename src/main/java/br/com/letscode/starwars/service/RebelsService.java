package br.com.letscode.starwars.service;

import br.com.letscode.starwars.enums.errors.RebelValidationError;
import br.com.letscode.starwars.exception.BusinessException;
import br.com.letscode.starwars.model.DTO.*;
import br.com.letscode.starwars.model.Entity.Inventory;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.model.Entity.Reports;
import br.com.letscode.starwars.repository.RebelsInventoryRepository;
import br.com.letscode.starwars.repository.RebelsRepository;
import br.com.letscode.starwars.repository.ReportsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.letscode.starwars.enums.errors.NegotiationValidationError.REBEL_SELLER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class RebelsService {

    private final RebelsRepository repository;
    private final RebelsInventoryRepository repositoryInventory;
    private final ReportsRepository repositoryReports;


    public RebelsCreatedResponse create(CreateRebelsRequest request){
        log.debug("Received rebel to create: {}",request);
        Inventory newInventory = Inventory.of(request.getInventory());
        Inventory savedInventory = repositoryInventory.save(newInventory);

        Rebel newRebel = Rebel.of(request);
        newRebel.setInventory(savedInventory);
        Rebel savedRebel = repository.save(newRebel);

        savedInventory.setRebel(savedRebel);
        repositoryInventory.save(savedInventory);

        return RebelsCreatedResponse.of(savedRebel);
    }

    public List<Rebel> getAllRebels(){
        return new ArrayList<>(repository.findAll());
    }

    public Rebel findById(Long id) {
        var optionalRebel = repository.findById(id);

        if (optionalRebel.isEmpty()){
            throw new BusinessException(RebelValidationError.NOT_FOUND_REBEL, "Não existe rebelde com o id: " + id);
        }

        return optionalRebel.get();
    }

    public ChangeRebelResponse changeRebel(Long id, ChangeRebelsRequest request) {
        Rebel receiveRebel = Rebel.of(request);
        Rebel changeRebel = this.findById(id);
        BeanUtils.copyProperties(receiveRebel, changeRebel, "rebel");
        changeRebel = repository.save(changeRebel);
        return ChangeRebelResponse.of(changeRebel);
    }

    public ChangeRebelResponse changeParcialRebel(Long id, ChangeRebelsRequest request) {
        Rebel receiveRebel = Rebel.of(request);
        Rebel changeRebel = this.findById(id);
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
        if(request.getLatitude() == null){
            ignoreProperties[6] = "latitud";
        }
        if(request.getLongitude() == null){
            ignoreProperties[7] = "longitud";
        }
        if(request.getBaseName() == null){
            ignoreProperties[8] = "baseName";
        }

        BeanUtils.copyProperties(request, changeRebel, ignoreProperties);
        changeRebel = repository.save(changeRebel);
        return ChangeRebelResponse.of(changeRebel);
    }

    public Reports createReport(Long idReportedRebel, Long idReporterRebel){
        var reported = repository.findById(idReportedRebel);
        var reporter = repository.findById(idReporterRebel);

        if(reported.isEmpty()){
            throw new BusinessException(REBEL_SELLER_NOT_FOUND, "Rebelde negociante não existe.");
        }

        if(reporter.isEmpty()){
            throw new BusinessException(REBEL_SELLER_NOT_FOUND, "Rebelde negociante não existe.");
        }

        repositoryReports.save(Reports.of(idReportedRebel,idReporterRebel));
        return Reports.of(idReportedRebel,idReporterRebel);
    }

}
