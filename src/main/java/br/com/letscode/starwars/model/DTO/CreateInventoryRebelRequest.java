package br.com.letscode.starwars.model.DTO;

import br.com.letscode.starwars.model.Entity.RebelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInventoryRebelRequest {

    private Long rebel;
    private Integer weapons;
    private Integer ammunition;
    private Integer waters;
    private Integer food;

}
