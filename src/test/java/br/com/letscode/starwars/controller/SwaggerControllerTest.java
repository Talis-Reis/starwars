package br.com.letscode.starwars.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class SwaggerControllerTest {
    @InjectMocks
    SwaggerController restController;

    @Test
    @DisplayName("Deve retornar o css para geração da documentação.")
    void shouldGenerateCSS() {
        var response = restController.getCss();
        assertNotNull(response);
    }

}