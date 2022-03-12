package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.Rebel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebelsRepository extends JpaRepository<Rebel, Long> {
}
