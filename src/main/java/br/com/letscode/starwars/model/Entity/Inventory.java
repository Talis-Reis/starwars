package br.com.letscode.starwars.model.Entity;

import br.com.letscode.starwars.model.DTO.CreateInventoryRebelRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="TB_INVENTORY")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventory;

    @Column(name = "WEAPONS")
    private Integer weapons;

    @Column(name = "AMMUNITION")
    private Integer ammunition;

    @Column(name = "WATERS")
    private Integer waters;

    @Column(name = "FOOD")
    private Integer food;

    @OneToOne
    @JoinColumn(name = "rebel_id", referencedColumnName = "rebel")
    private Rebel rebel;

    public static Inventory of(CreateInventoryRebelRequest request){
        Inventory inventory = new Inventory();
        Rebel rebel = new Rebel();
        rebel.setRebel(request.getRebel());
        BeanUtils.copyProperties(request, inventory);
        inventory.setRebel(rebel);
        return inventory;
    }
}
