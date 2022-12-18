package cz.cvut.fit.zimaluk1.tjv.tjveshop.domain;

import lombok.Data;


import javax.persistence.*;
import java.io.*;
import java.util.Objects;

@Entity
@Data
public class OrderProduct implements DomainEntity<Long>, Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "eorder_id")
    private Order order;
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;
    private Long amount;

    public OrderProduct(Long id, Order order, Product product, Long amount) {
        this.id = id;
        this.order = Objects.requireNonNull(order);
        this.product = Objects.requireNonNull(product);
        this.amount = amount;
    }
    public OrderProduct(){}

    @Override
    public Long getId(){
        return id;
    }
    public void setId(Long i){ this.id = i;}
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderProduct oprod = (OrderProduct) o;
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
