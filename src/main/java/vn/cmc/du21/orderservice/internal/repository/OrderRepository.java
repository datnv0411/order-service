package vn.cmc.du21.orderservice.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.internal.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
