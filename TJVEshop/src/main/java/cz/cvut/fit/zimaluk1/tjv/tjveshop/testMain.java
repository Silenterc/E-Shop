package cz.cvut.fit.zimaluk1.tjv.tjveshop;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.CustomerService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class testMain {
    public static void main(final String[] args) {
        Customer one = new Customer(0L, "Pepa", "ema","add", 3L);
        ApplicationContext ctx = new AnnotationConfigApplicationContext("cz.cvut.fit.zimaluk1.tjv.tjveshop");
        var cusS = ctx.getBean("CustomerService", CustomerService.class);
        cusS.deleteById(2L);
        try{
            cusS.create(one);
        } catch(Exception e){

        }
    }
}
