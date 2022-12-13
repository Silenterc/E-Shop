package cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto;

import lombok.Data;
import org.springframework.context.annotation.Bean;
@Data
public class CustomerDto {
    private String name;
    private String email;
    private String address;
    private Long money;

}
