package cz.cvut.fit.zimaluk1.tjv.tjveshop.domain;

import lombok.Data;
import org.modelmapper.internal.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Data
public class Customer implements DomainEntity<Long>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String address;
    private Long money;

    @OneToMany(mappedBy = "buyer")
    private Collection<Eorder> orders = new HashSet<Eorder>();

    public Customer(Long id, String name, String email, String address, Long money) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.address = Objects.requireNonNull(address);
        this.money = Objects.requireNonNull(money);
    }

    public Customer(){
    }
    public void addOrder(Eorder ord){
        orders.add(Objects.requireNonNull(ord));

    }
    @Override
    public Long getId(){
        return id;
    }
    @Override
    public void setId(Long i){ this.id = i;}
    /*
    Equals and hashcode have been inspired by the code from the presentations.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer cus = (Customer) o;
        if(getId() == null){
            return cus.getId() == null;
        }
        return getId().equals(cus.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }


}
