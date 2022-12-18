package cz.cvut.fit.zimaluk1.tjv.tjveshop.business;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.CustomerRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomerService extends CRUDService<Customer, Long>{
    private final OrderRepository orderRepository;
    public CustomerService(CustomerRepository cr, OrderRepository or){
        super(cr);
        orderRepository = or;
    }
    public Collection<Order> getAllOrders(Long id){
        return orderRepository.findAllByBuyerId(id);
    }

}
