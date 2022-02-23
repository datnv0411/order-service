package vn.cmc.du21.orderservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderPayment;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderPaymentId;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, OrderPaymentId> {
}
