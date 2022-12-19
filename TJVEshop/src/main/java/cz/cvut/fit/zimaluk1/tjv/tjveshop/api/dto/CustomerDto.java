package cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto;

import lombok.Data;
import org.springframework.context.annotation.Bean;
@Data
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private String address;
    private Long money;

    public CustomerDto() {
    }

    public CustomerDto(Long id, String name, String email, String address, Long money) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.money = money;
    }

    public void changeMoney(Long change){
        money += change;
    }

    public void edit(CustomerDto edited) {
        if(edited.name != null && !edited.name.isEmpty()){
            name = edited.name;
        }
        if(edited.email != null && !edited.email.isEmpty()){
            email = edited.email;
        }
        if(edited.address != null && !edited.address.isEmpty()){
            address = edited.address;
        }
        if(edited.money != null){
            money = edited.money;
        }
    }
}
