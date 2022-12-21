package cz.cvut.fit.zimaluk1.tjv.tjveshop.api;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.CustomerDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.OrderDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.CustomerService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
@RestController
@RequestMapping("/customers")
public class CustomerController extends CRUDController<Customer, CustomerDto, Long>{
    public CustomerController(CustomerService cus, ModelMapper mapp){
        super(cus, cusDto -> {
            return mapp.map(cusDto, Customer.class);},
                   cuss -> {
            return mapp.map(cuss, CustomerDto.class);
                   });
    }
    @GetMapping("/{id}/orders")
    @Operation(summary = "Gets all the Orders belonging to a Customer using his ID")
    public Collection<OrderDto> getAllOrders(@PathVariable Long id){
        Optional<Customer> buyer = ser.readById(id);
        if(buyer.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }
        Collection<Order> col = ((CustomerService)ser).getAllOrders(id);
        ArrayList<OrderDto> fin = new ArrayList<OrderDto>();
        for(Order e : col){
            fin.add(new OrderDto(e.getId(), e.getTime(), e.getState(), e.getBuyer().getId()));
        }
        Collections.reverse(fin);
        return fin;
    }

    /**
     * Gets the newest n orders from a Customer
     * @param id Customer id, whose orders I want to return
     * @param n The amount of orders I want to return(sorted by newest)
     * @return N newest orders
     */
    @GetMapping("/{id}/orders/{n}")
    @Operation(summary = "Gets up to n newest orders from a Customer using his ID")
    public Collection<OrderDto> getNOrders(@PathVariable("id") Long id, @PathVariable("n") Long n){
        Collection<Order> sorted = ((CustomerService)ser).getNOrders(id, n);
        Collection<OrderDto> fin = new ArrayList<>();
        for(Order e : sorted){
            fin.add(new OrderDto(e.getId(), e.getTime(), e.getState(), e.getBuyer().getId()));
        }
        return fin;
    }
}
