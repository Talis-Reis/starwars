package br.com.letscode.starwars.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class StartNegotiationRequest {
    private Long buyerRebel;
    private InventoryEmbedded requiredItems;
    private InventoryEmbedded availableItems;
}


