package br.com.letscode.starwars.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateRebelsRequest {
    private String name;
    private Integer age;
    private String genre;
    private Float latitude;
    private Float longitude;
    private String baseName;
    private InventoryEmbedded inventory;
}
