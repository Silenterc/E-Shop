package cz.cvut.fit.zimaluk1.tjv.tjveshop.api;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.ProductDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.ProductService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Product;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController extends CRUDController<Product, ProductDto, Long>{
    public ProductController(ProductService prod, ModelMapper mapp){
        super(prod, prodDto -> {
                    return mapp.map(prodDto, Product.class);},
                prodd -> {
                    return mapp.map(prodd, ProductDto.class);
                });
    }

}
