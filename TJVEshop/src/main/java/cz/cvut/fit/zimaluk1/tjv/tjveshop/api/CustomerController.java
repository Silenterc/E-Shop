package cz.cvut.fit.zimaluk1.tjv.tjveshop.api;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.CustomerDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.EorderDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.CustomerService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Eorder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    public Collection<EorderDto> getAllOrders(@PathVariable Long id){
        Optional<Customer> buyer = ser.readById(id);
        if(buyer.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }
        Collection<Eorder> col = ((CustomerService)ser).getAllOrders(id);
        ArrayList<EorderDto> fin = new ArrayList<EorderDto>();
        for(Eorder e : col){
            fin.add(new EorderDto(e.getTime(), e.getState(), e.getBuyer().getId()));
        }
        return fin;


    }
//    private CustomerDto convertToDto(Customer cus){
//        CustomerDto cusDto = mapper.map(cus, CustomerDto.class);
//        return cusDto;
//    }
//    private Customer convertToEntity(CustomerDto cusDto){
//        Customer cus = mapper.map(cusDto, Customer.class);
//
//        return cus;
//    }
}
