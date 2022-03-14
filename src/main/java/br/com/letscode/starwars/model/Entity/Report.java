package br.com.letscode.starwars.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name="TB_REPORTS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long report;

    @Column(name = "ID_REPORTER_REBEL")
    private Long idReporterRebel;

    @Column(name = "ID_REPORTED_REBEL")
    private Long idReportedRebel;

    public static Report of(Long idReporterRebel, Long idReportedRebel){
        Report report = new Report();
        report.setIdReporterRebel(idReporterRebel);
        report.setIdReportedRebel(idReportedRebel);
        return report;
    }
}
