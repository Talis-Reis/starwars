package br.com.letscode.starwars.service;

import br.com.letscode.starwars.exception.BusinessException;
import br.com.letscode.starwars.model.Entity.Report;
import br.com.letscode.starwars.model.Entity.Traitor;
import br.com.letscode.starwars.repository.RebelsRepository;
import br.com.letscode.starwars.repository.ReportRepository;
import br.com.letscode.starwars.repository.TraitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.letscode.starwars.enums.errors.ReportValidationError.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {

    private final RebelsRepository repository;
    private final ReportRepository repositoryReport;
    private final TraitorRepository repositoryTraitor;

    public Report createReport(Long idReporterRebel, Long idReportedRebel){
        var reported = repository.findById(idReportedRebel);
        var reporter = repository.findById(idReporterRebel);

        if(reported.isEmpty()){
            throw new BusinessException(REBEL_SELLER_NOT_FOUND, "Rebelde não existe.");
        }

        if(reporter.isEmpty()){
            throw new BusinessException(REBEL_SELLER_NOT_FOUND, "Rebelde não existe.");
        }

        if(!idReporterRebel.equals(idReportedRebel)){
            if(repositoryReport.getReportNumber(idReporterRebel,idReportedRebel).equals(0)){
                var numberReports = repositoryReport.getRebelReported(idReportedRebel);
                if(numberReports < 3){
                    System.out.println(repositoryReport.getRebelReported(idReportedRebel));
                    System.out.println("Qause lá");
                    repositoryReport.save(Report.of(idReporterRebel,idReportedRebel));

                    if(numberReports == 2){
                        repositoryTraitor.save(Traitor.of(idReportedRebel));
                    }

                    return Report.of(idReporterRebel,idReportedRebel);
                }else{
                    throw new BusinessException(THIS_REBEL_IS_A_TRAITOR, "O rebelde já é um traidor...");
                }
            }else{
                throw new BusinessException(REBELS_CAN_ONLY_REPORT_ONCE, "Apenas um report por rebelde...");
            }
        }else{
            throw new BusinessException(REBELS_CAN_ONLY_REPORT_ONCE, "Rebelde não pode se autoreportar...");
        }

    }

    public List<Traitor> getAllTraitors(){
        return new ArrayList<>(repositoryTraitor.findAll());
    }


}
