package br.com.letscode.starwars.service;

import br.com.letscode.starwars.model.DTO.OverviewResponse;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.model.Entity.Traitor;
import br.com.letscode.starwars.repository.RebelsInventoryRepository;
import br.com.letscode.starwars.repository.RebelsRepository;
import br.com.letscode.starwars.repository.TraitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OverviewService {
    private final RebelsRepository repositoryRebel;
    private final TraitorRepository repositoryTraitor;
    private final RebelsInventoryRepository repositoryInventory;

    public OverviewResponse generateResume(){
        var totalRebels = repositoryRebel.count();
        var totalTraitors = repositoryTraitor.count();
        float traitorPercent = 0.0f;

        if(totalRebels != 0){
            traitorPercent=  ((float) totalTraitors) / totalRebels;
        }

        float rebelPercent = 1 - traitorPercent;

        var traitorIDs = repositoryTraitor.findAll().stream().map(Traitor::getRebel).collect(Collectors.toSet());

        var lostPoints = repositoryRebel.findAllById(traitorIDs).stream().mapToInt(Rebel::calculateTotalPoints).sum();

        var avgItems = repositoryInventory.calculateAVG();

        var overview = new OverviewResponse();

        overview.setPercentRebel(rebelPercent);
        overview.setPercentTraitor(traitorPercent);
        overview.setItemsByRebel(avgItems);
        overview.setLostPoints(lostPoints);

        return overview;
    }

}
