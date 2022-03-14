package br.com.letscode.starwars.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OverviewResponse {
    private Float percentRebel;
    private Float percentTraitor;
    private InventoryAVG itemsByRebel;
    private Integer lostPoints;
}
