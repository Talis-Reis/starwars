package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.Entity.Report;
import br.com.letscode.starwars.model.Entity.Traitor;
import br.com.letscode.starwars.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class OverviewControllerTest {

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

}