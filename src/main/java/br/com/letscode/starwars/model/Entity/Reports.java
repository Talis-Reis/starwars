package br.com.letscode.starwars.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name="TB_REPORTS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long report;

    @Column(name = "ID_REPORTED_REBEL")
    private Long idReportedRebel;

    @Column(name = "ID_REPORTER_REBEL")
    private Long idReporterRebel;

    public static Reports of(Long idReportedRebel, Long idReporterRebel){
        Reports reports = new Reports();
        reports.setIdReportedRebel(idReportedRebel);
        reports.setIdReporterRebel(idReporterRebel);
        return reports;
    }


}
