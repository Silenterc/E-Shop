package cz.cvut.fit.zimaluk1.tjv.tjveshop.business;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.CustomerRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Eorder;
import org.springframework.stereotype.Service;

@Service
public class EorderService extends CRUDService<Eorder, Long>{
    private final CustomerRepository cusRep;
    EorderService(OrderRepository o, CustomerRepository c){
        super(o);
        cusRep = c;
    }
}
