package vn.cmc.du21.orderservice.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.internal.entity.OrderPayment;
import vn.cmc.du21.orderservice.internal.entity.OrderPaymentId;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, OrderPaymentId> {
}
