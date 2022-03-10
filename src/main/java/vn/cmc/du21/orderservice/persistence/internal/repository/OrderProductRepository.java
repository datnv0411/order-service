package vn.cmc.du21.orderservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderProduct;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderProductId;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    @Query(value = "select * from orderproduct where order_orderId = :orderId"
            , nativeQuery = true)
    List<OrderProduct> findByOrderProductId(@Param(value = "orderId") long orderId);
}
