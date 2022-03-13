package br.com.letscode.starwars.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartNegotiationRequest {
    private Long buyerRebel;
    private InventoryEmbedded requiredItems;
    private InventoryEmbedded availableItems;
}


