package cz.cvut.fit.zimaluk1.tjv.tjveshop.business;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.CustomerRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends CRUDService<Customer, Long>{
    public CustomerService(CustomerRepository cr){
        super(cr);
    }
}
