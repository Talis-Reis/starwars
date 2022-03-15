package br.com.letscode.starwars.service;

import br.com.letscode.starwars.enums.errors.NegotiationValidationError;
import br.com.letscode.starwars.enums.errors.RebelValidationError;
import br.com.letscode.starwars.enums.errors.ReportValidationError;
import br.com.letscode.starwars.exception.BusinessException;
import br.com.letscode.starwars.model.Entity.Rebel;
import br.com.letscode.starwars.repository.RebelsRepository;
import br.com.letscode.starwars.repository.ReportRepository;
import br.com.letscode.starwars.repository.TraitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ReportServiceTest {
    @InjectMocks
    ReportService reportService;

    @Mock
    RebelsRepository rebelRepository;
    @Mock
    TraitorRepository repositoryTraitor;
    @Mock
    ReportRepository repositoryReport;

    @BeforeEach
    void setUp() {
        Mockito.when(rebelRepository.findById(1L)).thenReturn(Optional.of(new Rebel()));
        Mockito.when(rebelRepository.findById(2L)).thenReturn(Optional.of(new Rebel()));
        Mockito.when(repositoryReport.getReportNumber(1L,2L)).thenReturn(0);
        Mockito.when(repositoryReport.getRebelReported(2L)).thenReturn(0);
        Mockito.when(rebelRepository.findById(2L)).thenReturn(Optional.of(new Rebel()));

    }

    @Test
    @DisplayName("Deve validar se o retorno do reporte válido.")
    void shouldCreateNewReport() {
        var response = reportService.createReport(1L, 2L);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Deve validar se a possiblidade 3 report por reportado.")
    void shouldCreateNewReportToAlreadyReported() {
        Mockito.when(repositoryReport.getRebelReported(2L)).thenReturn(2);

        var response = reportService.createReport(1L, 2L);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Deve validar se o retorno do reporte com denunciador inexistente.")
    void shouldThrowsNotFoundReporter() {
        Mockito.when(rebelRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            reportService.createReport(1L, 2L);
        });
        assertEquals(exception.getType(), ReportValidationError.REBEL_SELLER_NOT_FOUND);
    }

    @Test
    @DisplayName("Deve validar se o retorno do reporte com reportado inexistente.")
    void shouldThrowsNotFoundReported() {
        Mockito.when(rebelRepository.findById(2L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            reportService.createReport(1L, 2L);
        });
        assertEquals(exception.getType(), ReportValidationError.REBEL_SELLER_NOT_FOUND);
    }

    @Test
    @DisplayName("Deve validar se se trata de um auto reporte.")
    void shouldThrowsAutoReportError() {
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            reportService.createReport(1L, 1L);
        });
        assertEquals(exception.getType(), ReportValidationError.FORBIDDEN_REPORT_YOURSELF);
    }

    @Test
    @DisplayName("Deve validar se o reporte é repetido.")
    void shouldThrowsSameReportError() {
        Mockito.when(repositoryReport.getReportNumber(1L,2L)).thenReturn(1);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            reportService.createReport(1L, 2L);
        });
        assertEquals(exception.getType(), ReportValidationError.REBELS_CAN_ONLY_REPORT_ONCE);
    }

    @Test
    @DisplayName("Deve validar se o reportado ja é traidor.")
    void shouldThrowsReportedIsTraitorError() {
        Mockito.when(repositoryReport.getRebelReported(2L)).thenReturn(3);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            reportService.createReport(1L, 2L);
        });
        assertEquals(exception.getType(), ReportValidationError.THIS_REBEL_IS_A_TRAITOR);
    }

    @Test
    @DisplayName("Deve retornar todos dos traidores.")
    void shouldGetAllTraitor() {
        var response = reportService.getAllTraitors();
        assertNotNull(response);
    }
}