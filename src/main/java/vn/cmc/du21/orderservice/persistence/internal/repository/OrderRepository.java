package vn.cmc.du21.orderservice.persistence.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.cmc.du21.orderservice.persistence.internal.entity.Order;
import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select * from ordered where orderId = :orderId and userId = :userId"
            , nativeQuery = true)
    Order findOrderByOrderId(@Param(value = "userId") long userId, @Param(value = "orderId") long orderId);

    @Query(value = "select * from ordered where userId = :userId " +
            "and datediff(createTime, :endTime) <=0 " +
            "and datediff(createTime, :startTime) >=0 " +
            "order by createTime DESC"
            , nativeQuery = true)
    List<Order> findAllOrder(@Param(value = "userId") long userId
                            , @Param(value = "endTime") String endTime
                            , @Param(value = "startTime") String startTime);

    @Query(value = "select * from ordered where userId = :userId " +
            "and datediff(createTime, :endTime) <=0 " +
            "and datediff(createTime, :startTime) >=0 " +
            "and statusOrder = :statusOrder " +
            "order by createTime DESC"
            , nativeQuery = true)
    List<Order> findAllOrderByStatusOrder(@Param(value = "userId") long userId
            , @Param(value = "endTime") String endTime
            , @Param(value = "startTime") String startTime
            , @Param(value = "statusOrder") String statusOrder);
}
