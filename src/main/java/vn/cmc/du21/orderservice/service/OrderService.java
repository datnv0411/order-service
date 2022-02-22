package vn.cmc.du21.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cmc.du21.orderservice.persistence.internal.entity.*;
import vn.cmc.du21.orderservice.persistence.internal.repository.DeliveryAddressRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.OrderProductRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.OrderRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.VoucherRepository;
import vn.cmc.du21.orderservice.presentation.external.response.DeliveryAddressResponse;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    /*@Autowired
    OrderPayment orderPayment;*/
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

        // list products + size + quantity

        // orderaddress
        order.setDeliveryAddress(deliveryAddress);
        // ordervoucher
        List<Voucher> vouchers = new ArrayList<>();
        for (Voucher item : order.getVouchers()){
            Voucher voucher = voucherRepository.findByCodeVoucher(item.getCodeVoucher()).orElse(null);
        }
        // orderpayment
        return null;

    }

    public DeliveryAddress getDeliveryAddressByOrderId(long deliveryAddressId) {
        return deliveryAddressRepository.findById(deliveryAddressId).orElse(null);
    }
}
