package br.com.letscode.starwars.model.Entity;

import br.com.letscode.starwars.model.DTO.CreateInventoryRebelRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Table(name="TB_INVENTORY")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryEntity {

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rebel_id")
    private RebelEntity rebel_id;

    public static InventoryEntity of(CreateInventoryRebelRequest request){
        InventoryEntity inventory = new InventoryEntity();
        BeanUtils.copyProperties(request, inventory);
        return inventory;
    }

}
