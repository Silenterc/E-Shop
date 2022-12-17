package cz.cvut.fit.zimaluk1.tjv.tjveshop;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.CustomerService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.dao.CustomerRepository;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.ui.CookieReader;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.ui.NoIdNavigator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TjvEshopApplication {

    public static void main(String[] args){
        SpringApplication.run(TjvEshopApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public CookieReader cookieReader(){
        return new CookieReader();
    }
    @Bean
    public NoIdNavigator noIdNavigator(){
        return new NoIdNavigator();
    }
}
