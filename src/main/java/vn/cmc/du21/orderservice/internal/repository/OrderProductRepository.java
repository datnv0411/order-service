package vn.cmc.du21.orderservice.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.internal.entity.OrderProduct;
import vn.cmc.du21.orderservice.internal.entity.OrderProductId;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}
