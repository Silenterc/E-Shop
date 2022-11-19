package cz.cvut.fit.zimaluk1.tjv.tjveshop;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.CustomerRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TjvEshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TjvEshopApplication.class, args);

    }
}
