package br.com.letscode.starwars.model.DTO;

import br.com.letscode.starwars.model.Entity.InventoryEntity;
import br.com.letscode.starwars.model.Entity.RebelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RebelsInventoryCreatedResponse {

    private Long inventory;
    private Long rebel;
    private Integer weapons;
    private Integer ammunition;
    private Integer waters;
    private Integer food;


    public static RebelsInventoryCreatedResponse of(InventoryEntity rebelsInventory){
        RebelsInventoryCreatedResponse response = new RebelsInventoryCreatedResponse();
        RebelEntity rebel = rebelsInventory.getRebel();
        BeanUtils.copyProperties(rebelsInventory, response);
        response.setRebel(rebel.getRebel());
        return response;
    }

}
