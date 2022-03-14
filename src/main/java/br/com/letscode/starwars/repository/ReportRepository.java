package br.com.letscode.starwars.repository;

import br.com.letscode.starwars.model.Entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT COUNT(c.idReportedRebel) FROM Reports c WHERE c.idReportedRebel = ?1")
    Integer getRebelReported(Long idReported);

    @Query("SELECT COUNT(c.idReporterRebel) FROM Reports c WHERE c.idReporterRebel = ?1 AND c.idReportedRebel = ?2")
    Integer getReportNumber(Long idReporter, Long idReported);

}
