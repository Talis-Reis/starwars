package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.Reports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportsRepository extends JpaRepository<Reports, Long> {
}
