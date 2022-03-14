package br.com.letscode.starwars.model.Entity;

import br.com.letscode.starwars.model.DTO.InventoryEmbedded;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table(name="TB_INVENTORY")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long inventory;

    @NotBlank
    @Column(name = "WEAPONS")
    private Integer weapons;

    @NotBlank
    @Column(name = "AMMUNITION")
    private Integer ammunition;

    @NotBlank
    @Column(name = "WATERS")
    private Integer waters;

    @NotBlank
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

}
