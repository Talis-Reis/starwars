package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.Inventory;
import br.com.letscode.starwars.model.Entity.Punctuation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;

@Repository
public interface PunctuationRepository extends JpaRepository<Punctuation, Long> {

    @Query("SELECT c.weapons FROM Punctuation c")
    Integer getWeapons();

    @Query("SELECT c.ammunition FROM Punctuation c ")
    Integer getAmmunition();

    @Query("SELECT c.waters FROM Punctuation c")
    Integer getWaters();

    @Query("SELECT c.food FROM Punctuation c")
    Integer getFood();


}
