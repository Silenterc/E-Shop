package cz.cvut.fit.zimaluk1.tjv.tjveshop.api;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.OrderProductDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.OrderProductService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.OrderProduct;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Product;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/order_products")
public class OrderProductController extends CRUDController<OrderProduct, OrderProductDto, Long>{

    public OrderProductController(OrderProductService ordser, ModelMapper map){
        super(ordser,
                OrderProductDto -> {
                    Optional<Order> ord = ordser.getOrderById(OrderProductDto.getOrderId());
                    Optional<Product> prod = ordser.getProductById(OrderProductDto.getProductId());
                    if(ord.isEmpty() || prod.isEmpty()){
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
                    }
                    return new OrderProduct(OrderProductDto.getId(), ord.get(), prod.get(), OrderProductDto.getAmount());
                },
                OrderProduct -> {
                    return new OrderProductDto(OrderProduct.getId(), OrderProduct.getOrder().getId(), OrderProduct.getProduct().getId(), OrderProduct.getAmount());
                }
                );

    }
}
