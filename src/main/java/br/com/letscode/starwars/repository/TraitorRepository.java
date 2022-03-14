package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.Traitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraitorRepository extends JpaRepository<Traitor, Long> {
}
