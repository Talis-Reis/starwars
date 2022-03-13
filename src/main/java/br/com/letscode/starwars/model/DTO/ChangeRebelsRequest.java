package br.com.letscode.starwars.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeRebelsRequest {

    private String name;
    private Integer age;
    private String genre;
    private String latitud;
    private String longitud;
    private String baseName;
    private Integer traitor;
    private Integer reportsCounter;

}
