package cz.cvut.fit.zimaluk1.tjv.tjveshop.dao;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order_Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_ProductRepository extends JpaRepository<Order_Product, Long> {


}
