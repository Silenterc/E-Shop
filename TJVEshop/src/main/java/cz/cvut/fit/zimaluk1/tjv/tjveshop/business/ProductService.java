package cz.cvut.fit.zimaluk1.tjv.tjveshop.business;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.ProductRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends CRUDService<Product, Long>{
    public ProductService(ProductRepository p){
        super(p);
    }
}
