package vn.cmc.du21.orderservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderPayment;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderPaymentId;
import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;

import java.util.List;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, OrderPaymentId> {
}
