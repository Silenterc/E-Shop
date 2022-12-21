package cz.cvut.fit.zimaluk1.tjv.tjveshop.business;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderProductRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.ProductRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.OrderProduct;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderProductService extends CRUDService<OrderProduct, Long> {
    private final OrderRepository ordRep;
    private final ProductRepository prodRep;

    public OrderProductService(OrderProductRepository orp, OrderRepository ordRep, ProductRepository prodRep){
        super(orp);
        this.ordRep = ordRep;
        this.prodRep = prodRep;

    }
    public Optional<Order> getOrderById(Long id){
        return ordRep.findById(id);
    }
    public Optional<Product> getProductById(Long id){
        return prodRep.findById(id);
    }
}
