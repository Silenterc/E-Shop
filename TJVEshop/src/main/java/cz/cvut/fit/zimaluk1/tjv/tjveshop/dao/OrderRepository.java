package cz.cvut.fit.zimaluk1.tjv.tjveshop.dao;

import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Eorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderRepository extends JpaRepository<Eorder, Long> {
    Collection<Eorder> findAllByBuyerId(Long buyerId);




}
