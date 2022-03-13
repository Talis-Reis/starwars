package br.com.letscode.starwars.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

}
