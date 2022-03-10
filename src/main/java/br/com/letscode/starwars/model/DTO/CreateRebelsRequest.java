package br.com.letscode.starwars.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRebelsRequest {

    private String name;
    private Integer age;
    private String genre;
    private String latitud;
    private String longitud;
    private String baseName;
    private Integer traitor;
    private Integer reportsCounter;

}
