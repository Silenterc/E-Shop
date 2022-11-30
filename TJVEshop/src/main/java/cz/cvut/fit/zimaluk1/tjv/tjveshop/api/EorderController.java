package cz.cvut.fit.zimaluk1.tjv.tjveshop.api;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.EorderDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.EorderService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Eorder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class EorderController extends CRUDController<Eorder, EorderDto, Long>{
    public EorderController(EorderService ord, ModelMapper mapp){
        super(ord, ordDto -> {
            Optional<Customer> buyer = ord.getBuyerById(ordDto.getBuyerId());
            if(buyer.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
            }
            return new Eorder(null, ordDto.getTime(), ordDto.getState(),buyer.get());
            },
            ordd -> {
                Customer buy = ordd.getBuyer();
                if(buy.getId() == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
                }
                return new EorderDto(ordd.getTime(),ordd.getState(), ordd.getBuyer().getId());
            });
    }

}
