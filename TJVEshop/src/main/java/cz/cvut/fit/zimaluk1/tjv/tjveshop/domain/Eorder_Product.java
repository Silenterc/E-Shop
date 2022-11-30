package cz.cvut.fit.zimaluk1.tjv.tjveshop.domain;

import lombok.Data;


import javax.persistence.*;
import java.io.*;
import java.util.Objects;

@Entity
@Data
public class Eorder_Product implements DomainEntity<Long>, Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "eorder_id")
    private Eorder eorder;
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;
    private Long amount;

    public Eorder_Product(Long id, Eorder eorder, Product product, Long amount) {
        this.id = id;
        this.eorder = Objects.requireNonNull(eorder);
        this.product = Objects.requireNonNull(product);
        this.amount = amount;
    }
    public Eorder_Product(){}

    @Override
    public Long getId(){
        return id;
    }
    public void setId(Long i){ this.id = i;}
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Eorder_Product oprod = (Eorder_Product) o;
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
