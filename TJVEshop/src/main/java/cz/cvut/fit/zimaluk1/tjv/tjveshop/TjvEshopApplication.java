package cz.cvut.fit.zimaluk1.tjv.tjveshop;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TjvEshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TjvEshopApplication.class, args);
    }
    Customer l = new Customer();
}
