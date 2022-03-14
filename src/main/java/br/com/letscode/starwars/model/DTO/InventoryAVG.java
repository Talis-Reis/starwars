package br.com.letscode.starwars.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class InventoryAVG {
    private Double weapons;
    private Double ammunition;
    private Double waters;
    private Double food;

}
