package br.com.letscode.starwars.model.DTO;
import br.com.letscode.starwars.model.Entity.Rebel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRebelResponse {

    private Long rebel;
    private String name;
    private Integer age;
    private String genre;
    private String latitud;
    private String longitud;
    private String baseName;
    private Integer traitor;
    private Integer reportsCounter;

    public static ChangeRebelResponse of(Rebel rebels){
        ChangeRebelResponse response = new ChangeRebelResponse();
        BeanUtils.copyProperties(rebels, response);
        return response;
    }
}
