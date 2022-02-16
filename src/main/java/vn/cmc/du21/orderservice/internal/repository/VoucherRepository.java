package vn.cmc.du21.orderservice.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.internal.entity.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
}
