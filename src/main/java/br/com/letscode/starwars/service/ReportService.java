package br.com.letscode.starwars.service;

import br.com.letscode.starwars.exception.BusinessException;
import br.com.letscode.starwars.model.Entity.Report;
import br.com.letscode.starwars.model.Entity.Traitor;
import br.com.letscode.starwars.repository.ReportRepository;
import br.com.letscode.starwars.repository.TraitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.letscode.starwars.enums.errors.ReportValidationError.REBELS_CAN_ONLY_REPORT_ONCE;
import static br.com.letscode.starwars.enums.errors.ReportValidationError.THIS_REBEL_IS_A_TRAITOR;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository repositoryReports;
    private final TraitorRepository traitorRepository;

    public Report createReport(Long idReporterRebel, Long idReportedRebel){

        if(idReporterRebel != idReportedRebel){
            if(repositoryReports.getReportNumber(idReporterRebel,idReportedRebel) == 0){
                if(repositoryReports.getRebelReported(idReportedRebel) != 3){
                    repositoryReports.save(Report.of(idReporterRebel,idReportedRebel));
                    return Report.of(idReporterRebel,idReportedRebel);
                }else{
                    traitorRepository.save(Traitor.of(idReportedRebel));
                    throw new BusinessException(THIS_REBEL_IS_A_TRAITOR, "O rebelde já é um traidor...");
                }
            }else{
                throw new BusinessException(REBELS_CAN_ONLY_REPORT_ONCE, "Apenas um report por rebelde...");
            }
        }else{
            throw new BusinessException(REBELS_CAN_ONLY_REPORT_ONCE, "Rebelde não pode se autoreportar...");
        }

    }

}
