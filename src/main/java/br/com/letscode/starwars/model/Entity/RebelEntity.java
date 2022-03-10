package br.com.letscode.starwars.model.Entity;

import br.com.letscode.starwars.model.DTO.ChangeRebelsRequest;
import br.com.letscode.starwars.model.DTO.CreateRebelsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Table(name="TB_REBELS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RebelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rebel;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "GENRE")
    private String genre;

    @Column(name = "LATITUD")
    private String latitud;

    @Column(name = "LONGITUD")
    private String longitud;

    @Column(name = "BASENAME")
    private String baseName;

    @Column(name = "TRAITOR")
    private Integer traitor;

    @Column(name = "REPORTS_COUNTER")
    private Integer reportsCounter;

    @OneToOne(mappedBy = "rebel_id")
    private InventoryEntity inventory;


    public static RebelEntity of(CreateRebelsRequest request){
        RebelEntity rebel = new RebelEntity();
        BeanUtils.copyProperties(request, rebel);
        return rebel;
    }

    public static RebelEntity of(ChangeRebelsRequest request){
        RebelEntity rebel = new RebelEntity();
        BeanUtils.copyProperties(request, rebel);
        return rebel;
    }

}
