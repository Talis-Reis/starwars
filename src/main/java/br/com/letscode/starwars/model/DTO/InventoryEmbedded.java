package br.com.letscode.starwars.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryEmbedded {
    private Integer weapons;
    private Integer ammunition;
    private Integer waters;
    private Integer food;
}
