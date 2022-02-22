package vn.cmc.du21.orderservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.persistence.internal.entity.VoucherUser;
import vn.cmc.du21.orderservice.persistence.internal.entity.VoucherUserId;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherUserRepository extends JpaRepository<VoucherUser, VoucherUserId> {
    @Query(value = "SELECT * FROM VOUCHERUSER WHERE userId = :userId",nativeQuery = true)
    List<VoucherUser> findAllByVoucherUser_UserId(@Param(value = "userId") long userId);
    @Query(value = "SELECT * FROM VOUCHERUSER WHERE userId = :userId And voucher_voucherId = :voucherId",nativeQuery = true)
    Optional<VoucherUser> findAllByVoucherUser_UserId_VoucherId(@Param(value = "userId") long userId,@Param(value = "voucherId") long voucherId);
}
