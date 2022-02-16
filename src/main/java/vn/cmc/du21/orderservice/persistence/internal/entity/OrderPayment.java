package vn.cmc.du21.orderservice.persistence.internal.entity;

import javax.persistence.*;

@Entity
@Table(name = "orderpayment")
public class OrderPayment {
    @EmbeddedId
    private OrderPaymentId orderPaymentId;

    private String statusPayment;

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    public OrderPayment() {
    }

    public OrderPayment(OrderPaymentId orderPaymentId, String statusPayment, Order order) {
        this.orderPaymentId = orderPaymentId;
        this.statusPayment = statusPayment;
        this.order = order;
    }

    public OrderPaymentId getOrderPaymentId() {
        return orderPaymentId;
    }

    public void setOrderPaymentId(OrderPaymentId orderPaymentId) {
        this.orderPaymentId = orderPaymentId;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
