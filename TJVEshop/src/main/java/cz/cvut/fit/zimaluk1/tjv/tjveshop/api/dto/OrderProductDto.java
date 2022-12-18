package cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto;

import lombok.Data;

@Data
public class OrderProductDto {
    private Long id;
    private Long orderId;
    private Long productId;
    private Long amount;
    public OrderProductDto(Long id, Long ord, Long prod, Long amount){
        this.orderId = ord;
        this.productId = prod;
        this.amount = amount;
        this.id = id;
    }
}
