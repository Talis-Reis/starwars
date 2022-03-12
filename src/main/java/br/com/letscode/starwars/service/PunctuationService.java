package br.com.letscode.starwars.service;

import br.com.letscode.starwars.model.Entity.Punctuation;
import br.com.letscode.starwars.repository.PunctuationRepository;
import br.com.letscode.starwars.repository.RebelsInventoryRepository;
import br.com.letscode.starwars.repository.RebelsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class PunctuationService {

    private final PunctuationRepository repositoryPunctuation;
    private final RebelsRepository repository;
    private final RebelsInventoryRepository repositoryInventory;

    public void createPunctuation() {
        Punctuation punctuation = new Punctuation();
         Integer weapons = 4;
         Integer ammunition = 3;
         Integer waters = 2;
         Integer food = 1;

         punctuation.setWeapons(weapons);
         punctuation.setAmmunition(ammunition);
         punctuation.setWaters(waters);
         punctuation.setFood(food);

         repositoryPunctuation.save(punctuation);
    }


}
