package cz.cvut.fit.zimaluk1.tjv.tjveshop.dao;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


}
