package vn.cmc.du21.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.cmc.du21.orderservice.persistence.internal.entity.*;
import vn.cmc.du21.orderservice.persistence.internal.repository.DeliveryAddressRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.OrderProductRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.OrderRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.VoucherRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public OrderPayment getPaymentByOrderId(long userId, long orderId) {
        //return orderRepository.findOrderByOrderId(userId, orderId).getOrderPayments();
        return null;
    }

    @Transactional
    public Order updateOrder(long orderId, long userId) throws Throwable{
         Order foundOrder = orderRepository.findOrderByOrderId(userId, orderId);
         foundOrder.setStatusOrder("Cancel !!!");
         orderRepository.save(foundOrder);
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
        for(OrderProduct item : order.getOrderProducts())
        {
            item.setOrder(order);
        }
        // order address
        order.setDeliveryAddress(deliveryAddress);
        // order voucher
        List<Voucher> vouchers = new ArrayList<>();
        for (Voucher item : order.getVouchers()){
            Voucher voucher = voucherRepository.findByCodeVoucher(item.getCodeVoucher()).orElse(null);
            if(voucher!=null)
            {
                vouchers.add(voucher);
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
