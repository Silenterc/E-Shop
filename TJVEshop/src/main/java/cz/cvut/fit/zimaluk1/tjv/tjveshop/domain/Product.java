package cz.cvut.fit.zimaluk1.tjv.tjveshop.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Product implements DomainEntity<Long>, Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String price;
    private String amount;

    public Product(){}

    public Product(Long id, String name, String price, String amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
    @Override
    public Long getId(){
        return id;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product prod = (Product) o;
        if(getId() == null){
            return prod.getId() == null;
        }
        return getId().equals(prod.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }


}
