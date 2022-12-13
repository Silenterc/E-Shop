package cz.cvut.fit.zimaluk1.tjv.tjveshop.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
public class Product implements DomainEntity<Long>, Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long price;
    private Long amount;

    @OneToMany(mappedBy = "product")
    private Collection<OrderProduct> orderProducts = new HashSet<OrderProduct>();
    public Product(){}

    public Product(Long id, String name, Long price, Long amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
    @Override
    public Long getId(){
        return id;
    }
    @Override
    public void setId(Long i){ this.id = i;}
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
