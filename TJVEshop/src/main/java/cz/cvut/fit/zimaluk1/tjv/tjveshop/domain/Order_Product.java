package cz.cvut.fit.zimaluk1.tjv.tjveshop.domain;

import lombok.Data;


import javax.persistence.*;
import java.io.*;
import java.util.Objects;

@Entity
@Data
public class Order_Product implements DomainEntity<Long>, Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;
    private Long amount;

    public Order_Product(Long id, Order order, Product product, Long amount) {
        this.id = id;
        this.order = Objects.requireNonNull(order);
        this.product = Objects.requireNonNull(product);
        this.amount = amount;
    }
    public Order_Product(){}

    @Override
    public Long getId(){
        return id;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order_Product oprod = (Order_Product) o;
        if(getId() == null){
            return oprod.getId() == null;
        }
        return getId().equals(oprod.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
