package br.com.letscode.starwars.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name="TB_REBELSCORE")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RebelScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="TOTALPOINTS")
    private Integer totalPoints;

}
