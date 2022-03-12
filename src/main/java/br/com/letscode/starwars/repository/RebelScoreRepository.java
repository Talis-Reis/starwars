package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.RebelScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RebelScoreRepository extends JpaRepository<RebelScore, Integer> {
}
