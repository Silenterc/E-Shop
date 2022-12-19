package cz.cvut.fit.zimaluk1.tjv.tjveshop.dao;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    Collection<OrderProduct> findAllByOrderId(Long orderId);

}
