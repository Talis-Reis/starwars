package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.Traitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TraitorRepository extends JpaRepository<Traitor, Long> {
    @Query("SELECT t FROM Traitor t where t.rebel = ?1")
    Optional<Traitor> getTraitor(Long idRebel);

}
