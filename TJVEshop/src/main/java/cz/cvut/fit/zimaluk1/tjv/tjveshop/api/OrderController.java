package cz.cvut.fit.zimaluk1.tjv.tjveshop.api;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.OrderDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.ProductDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.CustomerService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.OrderService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Product;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController extends CRUDController<Order, OrderDto, Long>{
    public OrderController(OrderService ord, ModelMapper mapp){
        super(ord, ordDto -> {
            Optional<Customer> buyer = ord.getBuyerById(ordDto.getBuyerId());
            if(buyer.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
            }
            return new Order(ordDto.getId(), ordDto.getTime(), ordDto.getState(),buyer.get());
            },
            ordd -> {
                Customer buy = ordd.getBuyer();
                if(buy.getId() == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
                }
                return new OrderDto(ordd.getId(),ordd.getTime(),ordd.getState(), ordd.getBuyer().getId());
            });
    }
    @GetMapping("/{id}/products")
    public Collection<ProductDto> getAllProducts(@PathVariable Long id){
        Optional<Order> order = ser.readById(id);
        if(order.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }
        Collection<Product> col = ((OrderService)ser).getAllProducts(id);
        ArrayList<ProductDto> fin = new ArrayList<ProductDto>();
        for(Product e : col){
            fin.add(new ProductDto(e.getId(), e.getName(), e.getPrice(), e.getAmount()));
        }
        return fin;
    }

}
