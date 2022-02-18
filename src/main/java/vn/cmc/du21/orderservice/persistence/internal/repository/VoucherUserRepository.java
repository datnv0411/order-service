package vn.cmc.du21.orderservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.persistence.internal.entity.VoucherUser;
import vn.cmc.du21.orderservice.persistence.internal.entity.VoucherUserId;

@Repository
public interface VoucherUserRepository extends JpaRepository<VoucherUser, VoucherUserId> {
}
