package cz.cvut.fit.zimaluk1.tjv.tjveshop.api;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.CustomerDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.CustomerService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ModelMapper mapper;

    private CustomerDto convertToDto(Customer cus){
        CustomerDto cusDto = mapper.map(cus, CustomerDto.class);
        return cusDto;
    }
    private Customer convertToEntity(CustomerDto cusDto){
        Customer cus = mapper.map(cusDto, Customer.class);

        return cus;
    }

    @PostMapping
    @GetMapping("/customers")
    public CustomerDto create(@RequestBody CustomerDto c){
        System.err.println("j");
        Customer newCus = customerService.create(convertToEntity(c));
        //System.err.println(newCus.toString());
        //System.err.println(convertToDto(newCus).toString());
        return convertToDto(newCus);
    }
}
