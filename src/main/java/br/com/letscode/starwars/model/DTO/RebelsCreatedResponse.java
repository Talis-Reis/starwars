package br.com.letscode.starwars.model.DTO;

import br.com.letscode.starwars.model.Entity.RebelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RebelsCreatedResponse {

    private Long rebel;
    private String name;
    private Integer age;
    private String genre;
    private String latitud;
    private String longitud;
    private String baseName;
    private Integer traitor;
    private Integer reportsCounter;

    public static RebelsCreatedResponse of(RebelEntity rebels){
        RebelsCreatedResponse response = new RebelsCreatedResponse();
        BeanUtils.copyProperties(rebels, response);
        return response;
    }

}