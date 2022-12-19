package cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private Long price;
    private Long amount;

    public void changeAmount(int a){
        amount += a;
    }
    public ProductDto(){}

    public ProductDto(Long id, String name, Long price, Long amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public ProductDto(ProductDto copied){
        id = copied.getId();
        name = copied.getName();
        price = copied.getPrice();
        amount = 1L;
    }
}
