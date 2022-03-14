package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.Negotiation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NegotiationRepository extends JpaRepository<Negotiation, Long> {
    @Query("SELECT c FROM Negotiation c where c.sellerRebel.rebel = ?1 OR c.buyerRebel.rebel = ?1")
    List<Negotiation> getRebelNegotiations(Long id);

}
