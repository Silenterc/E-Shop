package cz.cvut.fit.zimaluk1.tjv.tjveshop.business;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.Order_ProductRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Eorder_Product;
import org.springframework.stereotype.Service;

@Service
public class Order_ProductService extends CRUDService<Eorder_Product, Long> {
    private final EorderService ordRep;
    private final ProductService prodRep;

    public Order_ProductService(Order_ProductRepository orp, EorderService ordRep, ProductService prodRep){
        super(orp);
        this.ordRep = ordRep;
        this.prodRep = prodRep;

    }
}
