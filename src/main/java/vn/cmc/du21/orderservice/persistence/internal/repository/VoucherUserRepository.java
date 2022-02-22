package vn.cmc.du21.orderservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.persistence.internal.entity.VoucherUser;
import vn.cmc.du21.orderservice.persistence.internal.entity.VoucherUserId;

import java.util.Optional;

@Repository
public interface VoucherUserRepository extends JpaRepository<VoucherUser, VoucherUserId> {
    @Query(value = "select * from voucheruser where voucher_voucherId = :voucherId and userId = :userId", nativeQuery = true)
    Optional<VoucherUser> findByVoucherIdAndUserId(@Param(value = "voucherId") long voucherId,
                                                   @Param(value = "userId")long userId);
}
