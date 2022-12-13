package cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private Long price;
    private Long amount;
}
