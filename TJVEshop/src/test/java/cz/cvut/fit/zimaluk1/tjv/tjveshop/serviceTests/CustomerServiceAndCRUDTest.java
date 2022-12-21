package cz.cvut.fit.zimaluk1.tjv.tjveshop.serviceTests;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.CustomerService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.CustomerRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.OrderRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Tests all the CRUD operations and Customer-specific operations.
 * There is no need to write CRUD tests for the other classes because
 * they all inherit from one CRUDService
 */
@SpringBootTest
public class CustomerServiceAndCRUDTest {
    @Autowired
    private CustomerService service;

    @MockBean
    private CustomerRepository repository;
    @MockBean
    private OrderRepository orepository;
    @Test
    public void createTest(){
        Customer newOne = new Customer(1L, "Lukas", "lukas@test.cz", "Praha", 100L);
        when(repository.save(newOne)).thenReturn(newOne);
        Assertions.assertEquals(newOne, service.create(newOne));
    }
    @Test
    public void updateTest(){
        //When the id is valid
        Customer newOne = new Customer(2L, "Lukas", "lukas@test.cz", "Praha", 100L);
        when(repository.existsById(2L)).thenReturn(true);
        when(repository.save(newOne)).thenReturn(newOne);
        try{
            Assertions.assertEquals(newOne, service.update(newOne));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //When the id is invalid
        when(repository.existsById(2L)).thenReturn(false);
        when(repository.save(newOne)).thenReturn(newOne);
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.update(newOne);
        });
        Assertions.assertTrue(exception.getMessage().contains("Cannot update"));
    }
    @Test
    public void readByIdTest(){
        //When the id is valid
        Customer newOne = new Customer(2L, "Lukas", "lukas@test.cz", "Praha", 100L);
        when(repository.findById(2L)).thenReturn(Optional.of(newOne));
        Assertions.assertEquals(newOne, service.readById(2L).get());

        //When the id is invalid
        when(repository.findById(2L)).thenReturn(Optional.empty());
        Assertions.assertTrue(service.readById(2L).isEmpty());
    }

    @Test
    public void deleteByIdTest(){
        service.deleteById(1L);
        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void readAllTest(){
        Customer newOne = new Customer(2L, "Lukas", "lukas@test.cz", "Praha", 100L);
        Customer secOne = new Customer(2L, "David", "lukas@test.cz", "Praha", 1000L);
        List<Customer> ret = new ArrayList<>();
        ret.add(newOne);
        ret.add(secOne);

        when(repository.findAll()).thenReturn(ret);
        Assertions.assertArrayEquals(ret.toArray(), service.readAll().toArray());

    }

    @Test
    public void getAllOrdersTest(){
        //Buyer has bought something
        Customer newOne = new Customer(1L, "Lukas", "lukas@test.cz", "Praha", 100L);
        Order newOrder = new Order(1L, null, "opened", newOne);
        Order secOrder = new Order(2L, null, "closed", newOne);
        Collection<Order> ret = new ArrayList<>();
        ret.add(newOrder);
        ret.add(secOrder);
        when(orepository.findAllByBuyerId(1L)).thenReturn(ret);
        Assertions.assertArrayEquals(ret.toArray(), service.getAllOrders(1L).toArray());

        //Buyer has not bought anything
        when(orepository.findAllByBuyerId(1L)).thenReturn(new ArrayList<Order>());
        Assertions.assertTrue(service.getAllOrders(1L).isEmpty());
    }
    @Test
    public void getNOrdersTest(){
        Customer newOne = new Customer(1L, "Lukas", "lukas@test.cz", "Praha", 100L);
        Order newOrder = new Order(1L, Timestamp.valueOf("2020-02-02 09:00:00"), "opened", newOne);
        Order secOrder = new Order(2L, Timestamp.valueOf("2021-02-02 09:00:00"), "closed", newOne);
        Order thirdOrder = new Order(3L, Timestamp.valueOf("2020-02-02 09:15:00"), "closed", newOne);
        Collection<Order> ret = new ArrayList<>();
        ret.add(newOrder);
        ret.add(secOrder);
        ret.add(thirdOrder);

        when(orepository.findAllByBuyerId(1L)).thenReturn(ret);
        //List of 2
        Collection<Order> got2 = service.getNOrders(1L, 2L);
        Assertions.assertEquals(2, got2.size());
        Assertions.assertTrue(checkSorted(new ArrayList<Order>(got2)));

        //List of 3
        Collection<Order> got3 = service.getNOrders(1L, 3L);
        Assertions.assertEquals(3, got3.size());
        Assertions.assertTrue(checkSorted(new ArrayList<Order>(got3)));

        //List of 4 should be the same as 3
        Collection<Order> got4 = service.getNOrders(1L, 4L);
        Assertions.assertEquals(3, got4.size());
        Assertions.assertTrue(checkSorted(new ArrayList<Order>(got4)));

    }
    private boolean checkSorted(ArrayList<Order> list){
        for(int i = 0 ; i < list.size() -1; i++) {
            if(list.get(i).getTime().compareTo(list.get(i+1).getTime()) <= 0 ){
                return false;
            }
        }
        return true;
    }

}
