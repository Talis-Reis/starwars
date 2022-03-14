package br.com.letscode.starwars.model.Entity;

import br.com.letscode.starwars.model.DTO.ChangeRebelsRequest;
import br.com.letscode.starwars.model.DTO.CreateRebelsRequest;
import br.com.letscode.starwars.model.DTO.InventoryEmbedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


@Table(name="TB_REBELS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rebel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rebel;

    @NotBlank
    @Column(name = "NAME")
    private String name;

    @NotBlank
    @Column(name = "AGE")
    @Positive
    private Integer age;

    @NotBlank
    @Column(name = "GENRE")
    private String genre;

    @NotBlank
    @Column(name = "LATITUDE")
    private Float latitude;

    @NotBlank
    @Column(name = "LONGITUDE")
    private Float longitude;

    @NotBlank
    @Column(name = "BASENAME")
    private String baseName;

    @OneToOne(mappedBy = "rebel")
    private Inventory inventory;

    public static Rebel of(CreateRebelsRequest request){
        Rebel rebel = new Rebel();
        BeanUtils.copyProperties(request, rebel);

        var inventory = new Inventory();
        BeanUtils.copyProperties(request.getInventory(), inventory);

        rebel.setInventory(inventory);
        return rebel;
    }

    public static Rebel of(ChangeRebelsRequest request){
        Rebel rebel = new Rebel();
        BeanUtils.copyProperties(request, rebel);
        return rebel;
    }

    public boolean hasItemsInInventory(InventoryEmbedded comparedInventory){
        return (this.inventory.getAmmunition() >= comparedInventory.getAmmunition())
                && (this.inventory.getFood() >= comparedInventory.getFood())
                && (this.inventory.getWaters() >= comparedInventory.getWaters())
                && (this.inventory.getWeapons() >= comparedInventory.getWeapons());
    }

    public Integer calculateTotalPoints(){
        return this.inventory.getPoints();
    }

}
