package cz.cvut.fit.zimaluk1.tjv.tjveshop.business;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.CustomerRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderProductRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.ProductRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.OrderProduct;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class OrderService extends CRUDService<Order, Long>{
    private final CustomerRepository cusRep;
    private final OrderProductRepository ordProdRep;
    private final ProductRepository prodRep;
    OrderService(OrderRepository o, CustomerRepository c, OrderProductRepository pr, ProductRepository p){
        super(o);
        cusRep = c;
        ordProdRep = pr;
        prodRep = p;
    }

    public Optional<Customer> getBuyerById(Long buyerId) {
        return cusRep.findById(buyerId);
    }

    /**
     * Return all Products belonging to an order
     * By manipulating with a decomposed M:N relation
     * @param orderId Id of the order
     * @return Collection of the orders belonging to the order
     */
    public Collection<Product> getAllProducts(Long orderId){
        Collection<OrderProduct> allJoins = ordProdRep.findAllByOrderId(orderId);
        Collection<Product> allProducts = new ArrayList<Product>();
        for(OrderProduct o : allJoins){
            Product newOne = new Product(o.getProduct());
            newOne.setAmount(o.getAmount());
            allProducts.add(newOne);
        }
        return allProducts;
    }
}
