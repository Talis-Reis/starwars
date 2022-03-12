package br.com.letscode.starwars.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name="TB_PONTUCTUATION")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Punctuation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "WEAPONS")
    private Integer weapons;

    @Column(name = "AMMUNITION")
    private Integer ammunition;

    @Column(name = "WATERS")
    private Integer waters;

    @Column(name = "FOOD")
    private Integer food;

}
