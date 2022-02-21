package vn.cmc.du21.orderservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query(value = "select v.voucherId, v.codeVoucher, v.startTime, v.endTime, v.timesOfUse, v.quantity" +
            ", v.image, v.title, v. percentValue, v.upToValue, v.applicableValue from ordervoucher ov " +
            "inner join voucher v on ov.voucherId = v.voucherId" +
            "inner join ordered o on ov.orderId = o.orderId " +
            "where orderId = :orderId and userId = :userId"
            , nativeQuery = true)
    List<Voucher> findVoucherByOrderId(@Param(value = "userId") long userId, @Param(value = "orderId") long orderId);
}
