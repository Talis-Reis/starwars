package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RebelsInventoryRepository extends JpaRepository<Inventory,Long> {
   @Query("SELECT c FROM Inventory c where c.inventory = ?1")
    Inventory getJoinInformation(Long id);
}
