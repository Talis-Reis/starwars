package br.com.letscode.starwars.model.Entity;

import br.com.letscode.starwars.enums.types.ItemType;
import br.com.letscode.starwars.model.DTO.InventoryEmbedded;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Table(name="TB_INVENTORY")
@Entity
@Data
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long inventory;

    @Column(name = "WEAPONS")
    private Integer weapons;

    @Column(name = "AMMUNITION")
    private Integer ammunition;

    @Column(name = "WATERS")
    private Integer waters;

    @Column(name = "FOOD")
    private Integer food;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "rebel_id", referencedColumnName = "rebel")
    private Rebel rebel;

    public static Inventory of(InventoryEmbedded request){
        Inventory inventory = new Inventory();
        BeanUtils.copyProperties(request, inventory);
        return inventory;
    }

    public void addItems(InventoryEmbedded inventoryItems){
        this.ammunition += inventoryItems.getAmmunition();
        this.food += inventoryItems.getFood();
        this.waters += inventoryItems.getWaters();
        this.weapons += inventoryItems.getWeapons();
    }

    public void removeItems(InventoryEmbedded inventoryItems){
        this.ammunition -= inventoryItems.getAmmunition();
        this.food -= inventoryItems.getFood();
        this.waters -= inventoryItems.getWaters();
        this.weapons -= inventoryItems.getWeapons();
    }

    public int getPoints(){
        return weapons *  ItemType.WEAPONS.getPrice()
                + ammunition * ItemType.AMMUNITION.getPrice()
                + waters * ItemType.WATERS.getPrice()
                + food * ItemType.FOOD.getPrice();
    }
}
