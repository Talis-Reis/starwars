package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebelsInventoryRepository extends JpaRepository<InventoryEntity,Long> {

//    @Query("SELECT c FROM InventoryEntity c")
//    List<InventoryEntity> getJoinInformation();
}
