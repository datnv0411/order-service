package vn.cmc.du21.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.cmc.du21.orderservice.persistence.internal.entity.*;
import vn.cmc.du21.orderservice.persistence.internal.repository.*;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    DeliveryAddressRepository deliveryAddressRepository;
    @Autowired
    VoucherUserRepository voucherUserRepository;

    @Transactional
    public Order getOrderByOrderId(long userId, long orderId) {
        return orderRepository.findOrderByOrderId(userId, orderId);
    }

    @Transactional
    public List<Voucher> getVoucherByOrderId(long userId, long voucherId) {
        return voucherRepository.findVoucherByOrderId(userId, voucherId);
    }

    @Transactional
    public List<OrderProduct> getProductByOrderId(long userId, long orderId) {
        return orderRepository.findOrderByOrderId(userId, orderId).getOrderProducts();
    }

    @Transactional
    public Order updateStatusOrder(long orderId, long userId, String status) throws Throwable{
         Order foundOrder = orderRepository.findOrderByOrderId(userId, orderId);
         if(foundOrder != null
             && (foundOrder.getStatusOrder().equals("Chờ thanh toán") ||  foundOrder.getStatusOrder().equals("Đặt món"))
             && !foundOrder.getStatusOrder().equals(status)){

             foundOrder.setStatusOrder(status);
             orderRepository.save(foundOrder);
         } else {
             throw new RuntimeException("Can't update status of order");
         }
         return foundOrder;
    }

    @Transactional
    public Order updateOrderAfterPaid(long orderId, long userId, String statusPaid) throws Throwable{
        Order foundOrder = orderRepository.findOrderByOrderId(userId, orderId);
        if(statusPaid != null && statusPaid.equals("Thành công")){
            foundOrder.setStatusOrder("Thành công");
            orderRepository.save(foundOrder);
        }
        return foundOrder;
    }

    @Transactional
    public long totalPrice(long orderId){
        List<OrderProduct> orderProducts = orderProductRepository.findByOrderProductId(orderId);
        long totalPrice = 0;
        for (OrderProduct item : orderProducts){
            totalPrice += item.getPriceSale() * item.getQuantity();
        }
        return totalPrice;
    }

    @Transactional
    public long totalDiscount(long orderId){
        List<OrderProduct> orderProducts = orderProductRepository.findByOrderProductId(orderId);
        long totalDiscount = 0;

        for (OrderProduct item : orderProducts){
            totalDiscount += item.getPrice() * item.getQuantity();
        }
        return totalPrice(orderId) - totalDiscount;
    }

    @Transactional
    public long shippingFee(long orderId){
        return 50000;
    }

    @Transactional
    public long totalBeforeVAT(long orderId){
        return totalPrice(orderId) + totalDiscount(orderId) + shippingFee(orderId);
    }

    @Transactional
    public long totalVAT(long orderId){
        return totalBeforeVAT(orderId) * 10 / 100;
    }

    @Transactional
    public long totalAfterVAT(long orderId){
        return totalBeforeVAT(orderId) + totalVAT(orderId);
    }

    @Transactional
    public long totalVoucherDiscount(long userId, long orderId){
        List<Voucher> vouchers = voucherRepository.findVoucherByOrderId(userId, orderId);
        long total = totalPrice(orderId) - totalDiscount(orderId);
        long totalVoucherDiscount = 0;
        for (Voucher item : vouchers){
            if(total * item.getPercentValue() > item.getUpToValue() * 100){
                totalVoucherDiscount += item.getUpToValue();
            } else {
                totalVoucherDiscount += total * item.getPercentValue() / 100;
            }
        }

        return 0 - totalVoucherDiscount;
    }

    @Transactional
    public long totalOrder(long userId, long orderId){
        return totalAfterVAT(orderId) - totalVoucherDiscount(userId, orderId);
    }

    @Transactional
    public Order createOrder(Order order, DeliveryAddress deliveryAddress){

        // order
        order.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        order.setStatusOrder("Chờ thanh toán");

        // list products + size + quantity
        long totalPrice = 0;
        for(OrderProduct item : order.getOrderProducts())
        {
            item.setOrder(order);
            totalPrice += item.getPriceSale() * item.getQuantity();
        }
        // order address
        order.setDeliveryAddress(deliveryAddress);
        // order voucher
        List<Voucher> vouchers = new ArrayList<>();
        for (Voucher item : order.getVouchers()){
            Voucher foundVoucher = voucherRepository.findAvailableVoucher(item.getCodeVoucher(), totalPrice, order.getUserId()).orElse(null);
            if(foundVoucher!=null)
            {
                foundVoucher.setQuantity(foundVoucher.getQuantity() -1);
                voucherRepository.save(foundVoucher);

                Optional<VoucherUser> voucherUser =
                        voucherUserRepository.findByVoucherIdAndUserId(foundVoucher.getVoucherId(), order.getUserId());
                if(voucherUser.isPresent())
                {
                    voucherUser.get().setUsedTimes(voucherUser.get().getUsedTimes() + 1);
                }
                else
                {
                    throw new RuntimeException("Voucher cannot be applied, please try again !!!");
                }
                voucherUserRepository.save(voucherUser.get());

                vouchers.add(foundVoucher);
            }
            else
            {
                throw new RuntimeException("Voucher cannot be applied, please try again !!!");
            }
        }
        order.setVouchers(vouchers);

        return orderRepository.save(order);
    }

    public DeliveryAddress getDeliveryAddressByOrderId(long deliveryAddressId) {
        return deliveryAddressRepository.findById(deliveryAddressId).orElse(null);
    }

    @Transactional
    public Page<Order> getListOrder(int page, int size, String status, String startTime, String endTime, long userId){
        List<Order> orders;

        if (status.equals("all")){
            orders = orderRepository.findAllOrder(userId, endTime, startTime);
        } else {
            orders = orderRepository.findAllOrderByStatusOrder(userId, endTime, startTime, status);
        }

        Pageable pageable = PageRequest.of(page, size);
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), orders.size());
        return new PageImpl<>(orders.subList(start, end), pageable, orders.size());
    }
}
