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
public class RebelsCreatedResponse {
    private Long rebel;
    private String name;
    private Integer age;
    private String genre;
    private Float latitude;
    private Float longitude;
    private String baseName;
    private InventoryEmbedded inventory;

    public static RebelsCreatedResponse of(Rebel rebels){
        RebelsCreatedResponse response = new RebelsCreatedResponse();

        var inventory = new InventoryEmbedded();
        BeanUtils.copyProperties(rebels.getInventory(), inventory);

        BeanUtils.copyProperties(rebels, response);
        response.setInventory(inventory);
        return response;
    }
}