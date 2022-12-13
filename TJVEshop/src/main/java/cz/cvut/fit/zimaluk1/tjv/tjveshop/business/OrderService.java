package cz.cvut.fit.zimaluk1.tjv.tjveshop.business;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.CustomerRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService extends CRUDService<Order, Long>{
    private final CustomerRepository cusRep;
    OrderService(OrderRepository o, CustomerRepository c){
        super(o);
        cusRep = c;
    }

    public Optional<Customer> getBuyerById(Long buyerId) {
        return cusRep.findById(buyerId);
    }
}
