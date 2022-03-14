package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.Entity.Report;
import br.com.letscode.starwars.model.Entity.Traitor;
import br.com.letscode.starwars.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class ReportRestControllerTest {

    @InjectMocks
    ReportRestController restController;

    @Mock
    ReportService reportService;

    @Mock
    Report report;

    @Mock
    List<Traitor> traitorList;

    @BeforeEach
    void setup() {
        report = Report
                .builder()
                .idReporterRebel(1L)
                .idReportedRebel(2L)
                .build();

        traitorList = new ArrayList<>();
        traitorList.add(Traitor.of(report.getIdReportedRebel()));
    }

    @Test
    @DisplayName("Report")
    void reportRebel() {
        Mockito.when(reportService.createReport(report.getIdReporterRebel(), report.getIdReportedRebel())).thenReturn(Report.of(report.getIdReporterRebel(),report.getIdReportedRebel()));

        Report response = restController.createReport(report.getIdReporterRebel(), report.getIdReportedRebel());;
        Report reportInject = reportService.createReport(report.getIdReporterRebel(), report.getIdReportedRebel());

        assertNotNull(response);

        assertEquals(reportInject,response);

    }

    @Test
    @DisplayName("Deve retornar todos os rebeldes salvos em um repositório.")
    void getTraitorsAll() {
        Mockito.when(reportService.getAllTraitors()).thenReturn(traitorList);
        List<Traitor> returnTraitor = restController.getAll();

        assertEquals(returnTraitor, traitorList);
        assertNotNull(traitorList);
    }

}