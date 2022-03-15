package br.com.letscode.starwars.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name="TB_TRAITORS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Traitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long traitor;

    @Column(name = "ID_REBEL_TRAITOR")
    private Long rebel;


    public static Traitor of(Long id){
        Traitor traitor = new Traitor();
        traitor.setRebel(id);
        return traitor;
    }

}
