package br.com.letscode.starwars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RebelsCreatedResponse {

    private Long IDRebel;
    private String name;
    private Integer age;
    private String genre;
    private String latitud;
    private String longitud;
    private String baseName;
    private Integer reportsCounter;


    public static RebelsCreatedResponse of(Rebels rebels){
        RebelsCreatedResponse response = new RebelsCreatedResponse();
        BeanUtils.copyProperties(rebels, response);
        return response;
    }

}
