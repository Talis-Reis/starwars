package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.Entity.Report;
import br.com.letscode.starwars.model.Entity.Traitor;
import br.com.letscode.starwars.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/rebels")
@Tag(name="Report")
@AllArgsConstructor
@Slf4j
public class ReportRestController {

    private final ReportService reportService;

    @PostMapping(value = "/{id}/report/{idReported}")
    @ResponseStatus(HttpStatus.CREATED)
    public Report createReport(@PathVariable("id") Long idReporterRebel, @PathVariable("idReported") Long idReportedRebel){
        log.info("Rebel reporter: {}", idReporterRebel);
        Report report = reportService.createReport(idReporterRebel,idReportedRebel);
        log.info("Rebel reported: {}", idReportedRebel);
        return report;
    }

    @GetMapping(value = "/traitors")
    public List<Traitor> getAll(){
        List<Traitor> traitors = reportService.getAllTraitors();
        return traitors;
    }
}
