package vn.cmc.du21.orderservice.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.internal.entity.OrderVoucher;
import vn.cmc.du21.orderservice.internal.entity.OrderVoucherId;

@Repository
public interface OrderVoucherRepository extends JpaRepository<OrderVoucher, OrderVoucherId> {
}
