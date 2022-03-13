package br.com.letscode.starwars.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/swagger-ui")
public class SwaggerController {
    @Operation(hidden = true)
    @GetMapping(path="/swagger-ui.css", produces = "text/css")
    public String getCss() {
        String orig = toText(getClass().getResourceAsStream("/META-INF/resources/webjars/swagger-ui/4.5.0/swagger-ui.css"));
        System.out.println(getClass().getResource("."));
        String append = toText(getClass().getResourceAsStream("/swagger-ui.css"));
        return orig + append;
    }

    static String toText(InputStream in) {
        return new BufferedReader( new InputStreamReader(in, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));
    }
}