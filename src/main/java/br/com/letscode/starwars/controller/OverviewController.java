package br.com.letscode.starwars.controller;

import br.com.letscode.starwars.model.DTO.InventoryAVG;
import br.com.letscode.starwars.model.DTO.OverviewResponse;
import br.com.letscode.starwars.service.OverviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/overview")
@Tag(name="Overviews")
@AllArgsConstructor
@Slf4j
public class OverviewController {
    private OverviewService overviewService;

    @GetMapping
    public OverviewResponse generateOverview(){
        return overviewService.generateResume();
    }
}
