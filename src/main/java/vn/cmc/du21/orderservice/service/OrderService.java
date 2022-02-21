package vn.cmc.du21.orderservice.service;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.orderservice.persistence.internal.entity.Order;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderPayment;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderProduct;
import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;
import vn.cmc.du21.orderservice.persistence.internal.repository.OrderProductRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.OrderRepository;
import vn.cmc.du21.orderservice.persistence.internal.repository.VoucherRepository;
import vn.cmc.du21.orderservice.presentation.internal.response.AddressResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.PaymentResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.UserResponse;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
}
