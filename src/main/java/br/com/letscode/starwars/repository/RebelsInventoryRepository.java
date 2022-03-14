package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RebelsInventoryRepository extends JpaRepository<Inventory, Long> {
}
