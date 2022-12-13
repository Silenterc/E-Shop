package cz.cvut.fit.zimaluk1.tjv.tjveshop.business;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderProductRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.OrderProduct;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderProductService extends CRUDService<OrderProduct, Long> {
    private final OrderService ordRep;
    private final ProductService prodRep;

    public OrderProductService(OrderProductRepository orp, OrderService ordRep, ProductService prodRep){
        super(orp);
        this.ordRep = ordRep;
        this.prodRep = prodRep;

    }
    public Optional<Order> getOrderById(Long id){
        return ordRep.readById(id);
    }
    public Optional<Product> getProductById(Long id){
        return prodRep.readById(id);
    }
}
