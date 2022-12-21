package cz.cvut.fit.zimaluk1.tjv.tjveshop.repositoryTests;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.CustomerRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderProductRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.ProductRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.OrderProduct;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderProductRepositoryTest {
    @Autowired
    OrderProductRepository oprepo;
    @Autowired
    ProductRepository prodrepo;
    @Autowired
    OrderRepository orepository;
    @Autowired
    CustomerRepository cusrepository;
    @AfterEach
    void deleteAll() {
        oprepo.deleteAll();
        orepository.deleteAll();
        cusrepository.deleteAll();
        prodrepo.deleteAll();
    }
    @Test
    public void findAllByOrderIDTest(){
        Customer newCus = new Customer(1L, "Lukas", "email", "Praha", 200L);
        Customer secCus = new Customer(2L, "David", "mail", "Praha", 1000L);
        cusrepository.save(newCus);
        cusrepository.save(secCus);

        Order newOne = new Order(1L, null, "opened",newCus);
        Order secOne = new Order(2L, null, "closed",newCus);
        Order thirdOne = new Order(3L, null, "processing",secCus);
        Order newOneSaved = orepository.save(newOne);
        Order secOneSaved = orepository.save(secOne);
        Order thirdOneSaved = orepository.save(thirdOne);

        Product newProd = new Product(4L, "Iphone", 20000L, 1L );
        Product secProd = new Product(5L, "TShirt", 200L, 3L );
        Product thirdProd = new Product(6L, "Book", 300L, 2L );
        prodrepo.save(newProd);
        prodrepo.save(secProd);
        prodrepo.save(thirdProd);

        OrderProduct newOrderProd = new OrderProduct(1L, newOne, newProd, 1L);
        OrderProduct secOrderProd = new OrderProduct(2L, newOne, secProd, 2L);
        OrderProduct thirdOrderProd = new OrderProduct(3L, secOne, thirdProd, 2L);

        oprepo.save(newOrderProd);
        oprepo.save(secOrderProd);
        oprepo.save(thirdOrderProd);

        Assertions.assertEquals(2, oprepo.findAllByOrderId(newOneSaved.getId()).size());
        Assertions.assertEquals(1, oprepo.findAllByOrderId(secOneSaved.getId()).size());
        Assertions.assertEquals(0, oprepo.findAllByOrderId(thirdOneSaved.getId()).size());
    }
}
