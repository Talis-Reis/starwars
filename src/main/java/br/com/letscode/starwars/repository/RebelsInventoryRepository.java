package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.DTO.InventoryAVG;
import br.com.letscode.starwars.model.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RebelsInventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT new br.com.letscode.starwars.model.DTO.InventoryAVG(AVG(i.weapons), AVG(i.ammunition),AVG(i.waters),AVG(i.food) ) " +
            "FROM Inventory i")
    InventoryAVG calculateAVG();
}