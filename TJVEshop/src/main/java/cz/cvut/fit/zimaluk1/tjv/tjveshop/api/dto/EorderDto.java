package cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import lombok.Data;

import javax.persistence.ManyToOne;
import java.sql.Timestamp;
@Data
public class EorderDto {
    private Timestamp time;
    private String state;
    private Long buyerId;

    public EorderDto(Timestamp time, String state, Long buyerId) {
        this.time = time;
        this.state = state;
        this.buyerId = buyerId;
    }
}
