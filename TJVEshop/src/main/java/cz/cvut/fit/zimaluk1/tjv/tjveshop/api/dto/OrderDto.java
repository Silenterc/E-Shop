package cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class OrderDto {
    private Long id;
    @JsonFormat(timezone = "Europe/Amsterdam")
    private Timestamp time;
    private String state;
    private Long buyerId;
    public OrderDto(){}

    public OrderDto(Long id, Timestamp time, String state, Long buyerId) {
        this.time = time;
        this.state = state;
        this.buyerId = buyerId;
        this.id = id;
    }
}
