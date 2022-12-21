package cz.cvut.fit.zimaluk1.tjv.tjveshop.serviceTests;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.OrderService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.CustomerRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderProductRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.ProductRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.OrderProduct;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService service;
    @MockBean
    private CustomerRepository cusRep;
    @MockBean
    private OrderProductRepository ordProdRep;
    @MockBean
    private ProductRepository prodRep;
    @Test
    public void getBuyerByIdTest(){
        //When the id is valid
        Customer newOne = new Customer(2L, "Lukas", "lukas@test.cz", "Praha", 100L);
        when(cusRep.findById(2L)).thenReturn(Optional.of(newOne));
        Assertions.assertEquals(newOne, service.getBuyerById(2L).get());

        //When the id is invalid
        when(cusRep.findById(2L)).thenReturn(Optional.empty());
        Assertions.assertTrue(service.getBuyerById(2L).isEmpty());
    }
    @Test
    public void getAllProductsTest(){
        Customer newCus = new Customer(2L, "Lukas", "lukas@test.cz", "Praha", 100L);
        Order newOrder = new Order(1L, Timestamp.valueOf("2020-02-02 09:00:00"), "opened", newCus);

        Product firstProd = new Product(1L, "Iphone", 12000L, 5L);
        Product secondProd = new Product(2L, "Macbook", 30000L, 4L);
        Product thirdProd = new Product(3L, "Book", 200L, 20L);

        OrderProduct firstOne = new OrderProduct(1L, newOrder, firstProd, 2L);
        OrderProduct secondOne = new OrderProduct(2L, newOrder, secondProd, 1L);
        OrderProduct thirdOne = new OrderProduct(3L, newOrder, thirdProd, 15L);
        //It calls the OrderProduct repo to get all the decomposed M:N entries
        when(ordProdRep.findAllByOrderId(1L)).thenReturn(Arrays.asList(firstOne, secondOne, thirdOne));

        ArrayList<Product> ret = new ArrayList<Product>(service.getAllProducts(1L));

        Assertions.assertEquals(3, ret.size());
        //Check if the amounts are correct
        Assertions.assertEquals(2L, ret.get(0).getAmount());
        Assertions.assertEquals(1L, ret.get(1).getAmount());
        Assertions.assertEquals(15L, ret.get(2).getAmount());


    }
}
