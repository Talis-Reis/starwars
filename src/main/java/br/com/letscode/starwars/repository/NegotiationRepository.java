package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.Negotiation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegotiationRepository extends JpaRepository<Negotiation, Long> {

}
