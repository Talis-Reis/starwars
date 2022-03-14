package br.com.letscode.starwars.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInventoryRebelRequest {
    private Integer weapons;
    private Integer ammunition;
    private Integer waters;
    private Integer food;
}
