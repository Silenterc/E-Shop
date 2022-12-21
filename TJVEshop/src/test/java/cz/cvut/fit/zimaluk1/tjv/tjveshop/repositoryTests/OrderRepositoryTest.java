package cz.cvut.fit.zimaluk1.tjv.tjveshop.repositoryTests;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.CustomerRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderRepositoryTest {
    @Autowired
    OrderRepository orepository;
    @Autowired
    CustomerRepository cusrepository;

    @AfterEach
    void deleteAll() {
        orepository.deleteAll();
        cusrepository.deleteAll();
    }

    @Test
    public void findAllByBuyerIDTest(){
        Customer newCus = new Customer(1L, "Lukas", "email", "Praha", 200L);
        Customer secCus = new Customer(2L, "David", "mail", "Praha", 1000L);
        Customer savedCus = cusrepository.save(newCus);
        Customer savedCusSec = cusrepository.save(secCus);
        Order newOne = new Order(1L, null, "opened",newCus);
        Order secOne = new Order(2L, null, "closed",newCus);
        Order thirdOne = new Order(3L, null, "processing",secCus);
        orepository.save(newOne);
        orepository.save(secOne);
        orepository.save(thirdOne);

        Assertions.assertEquals(2, orepository.findAllByBuyerId(savedCus.getId()).size());
        Assertions.assertEquals(1, orepository.findAllByBuyerId(savedCusSec.getId()).size());
        Assertions.assertEquals(0, orepository.findAllByBuyerId(-1L).size());
    }
}
