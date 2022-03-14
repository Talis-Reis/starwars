package br.com.letscode.starwars.model.Entity;

import br.com.letscode.starwars.enums.types.ItemType;
import br.com.letscode.starwars.model.DTO.StartNegotiationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Table(name="TB_NEGOTIATION")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Negotiation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long negotiation;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_rebel_id", referencedColumnName = "rebel")
    private Rebel sellerRebel;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer_rebel_id", referencedColumnName = "rebel")
    private Rebel buyerRebel;

    @Column(name = "REQUIRED")
    @Embedded
    private InventoryTransition requiredItems;

    @Column(name = "AVAILABLE")
    @Embedded
    private InventoryTransition availableItems;

    public static Negotiation of(Rebel sellerRebel,  Rebel buyerRebel, StartNegotiationRequest request){
        Negotiation negotiation = new Negotiation();

        InventoryTransition requiredItems = new InventoryTransition();
        BeanUtils.copyProperties(request.getRequiredItems(), requiredItems);

        InventoryTransition availableItems = new InventoryTransition();
        BeanUtils.copyProperties(request.getAvailableItems(), availableItems);

        BeanUtils.copyProperties(request, negotiation);
        negotiation.setRequiredItems(requiredItems);
        negotiation.setAvailableItems(availableItems);
        negotiation.setSellerRebel(sellerRebel);
        negotiation.setBuyerRebel(buyerRebel);
        return negotiation;
    }

    public boolean isFair(){
        return requiredItems.calculatePoints() == availableItems.calculatePoints();
    }
}



@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
class InventoryTransition{
    private Integer weapons;
    private Integer ammunition;
    private Integer waters;
    private Integer food;

    public int calculatePoints(){
        return weapons *  ItemType.WEAPONS.getPrice()
                + ammunition * ItemType.AMMUNITION.getPrice()
                + waters * ItemType.WATERS.getPrice()
                + food * ItemType.FOOD.getPrice();
    }
}