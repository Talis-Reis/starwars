package br.com.letscode.starwars.model.DTO;


import br.com.letscode.starwars.model.Entity.Negotiation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartedNegotiationResponse {
    private Long negotiation;
    private Long sellerRebel;
    private Long buyerRebel;
    private InventoryEmbedded requiredItems;
    private InventoryEmbedded availableItems;

    public static StartedNegotiationResponse of(Negotiation negotiation){
        StartedNegotiationResponse response = new StartedNegotiationResponse();

        var requiredItems = new InventoryEmbedded();
        BeanUtils.copyProperties(negotiation.getRequiredItems(), requiredItems);

        var availableItems = new InventoryEmbedded();
        BeanUtils.copyProperties(negotiation.getAvailableItems(), availableItems);

        response.setRequiredItems(requiredItems);
        response.setAvailableItems(availableItems);
        response.setSellerRebel(negotiation.getSellerRebel().getRebel());
        response.setBuyerRebel(negotiation.getBuyerRebel().getRebel());
        response.setNegotiation(negotiation.getNegotiation());
        return response;
    }
}
