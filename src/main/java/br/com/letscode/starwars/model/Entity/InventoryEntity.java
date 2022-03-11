package br.com.letscode.starwars.model.Entity;

import br.com.letscode.starwars.model.DTO.CreateInventoryRebelRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Table(name="TB_INVENTORY")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryEntity implements Serializable {

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
    private RebelEntity rebel;

    public static InventoryEntity of(CreateInventoryRebelRequest request){
        InventoryEntity inventory = new InventoryEntity();
        BeanUtils.copyProperties(request, inventory);
        return inventory;
    }
}
