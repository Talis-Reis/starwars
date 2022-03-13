package br.com.letscode.starwars.model.DTO;
import br.com.letscode.starwars.model.Entity.Rebel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeRebelResponse {

    private Long rebel;
    private String name;
    private Integer age;
    private String genre;
    private Float latitude;
    private Float longitude;
    private String baseName;

    public static ChangeRebelResponse of(Rebel rebels){
        ChangeRebelResponse response = new ChangeRebelResponse();
        BeanUtils.copyProperties(rebels, response);
        return response;
    }
}
