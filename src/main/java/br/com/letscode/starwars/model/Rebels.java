package br.com.letscode.starwars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rebels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long IDRebel;
    private String name;
    private Integer age;
    private String genre;
    private String latitud;
    private String longitud;
    private String baseName;
    private Integer reportsCounter;

    public static Rebels of(CreateRebelsRequest request){
        Rebels rebel = new Rebels();
        BeanUtils.copyProperties(request, rebel);
        return rebel;
    }
}
