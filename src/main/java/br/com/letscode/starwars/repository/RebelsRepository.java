package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.RebelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebelsRepository extends JpaRepository<RebelEntity, Long> {

//    @Query("SELECT c FROM InventoryEntity c JOIN c.RebelEntity p")
//    public List<RebelEntity> getJoinInformation();
}
