package cz.cvut.fit.zimaluk1.tjv.tjveshop.business;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.CustomerRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

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

    /**
     * Gets all orders, sorts them by their time, so the newest is first and then returns the first n orders.
     * @param id Customer id
     * @param n Amount of orders I want to return
     * @return N Newest orders by customer
     */
    public Collection<Order> getNOrders(Long id, Long n){
        Collection<Order> all = getAllOrders(id);
        ArrayList<Order> sorted = new ArrayList<Order>(all);
        sorted.sort(Collections.reverseOrder());

        return sorted.stream().limit(n).collect(Collectors.toList());
    }

}
