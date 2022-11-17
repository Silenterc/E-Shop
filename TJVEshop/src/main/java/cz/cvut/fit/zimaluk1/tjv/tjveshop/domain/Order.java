package cz.cvut.fit.zimaluk1.tjv.tjveshop.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Data
public class Order implements DomainEntity<Long>, Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Timestamp time;
    private String state;
    @ManyToOne
    private Customer buyer;

    public Order(Long id, Timestamp time, String state, Customer buyer) {
        this.id = id;
        this.time = time;
        this.state = state;
        this.buyer = Objects.requireNonNull(buyer);
    }
    public Order(){}
    @Override
    public Long getId(){
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order ord = (Order) o;
        if(getId() == null){
            return ord.getId() == null;
        }
        return getId().equals(ord.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}