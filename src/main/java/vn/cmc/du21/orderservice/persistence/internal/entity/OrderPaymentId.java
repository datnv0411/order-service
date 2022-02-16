package vn.cmc.du21.orderservice.persistence.internal.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderPaymentId implements Serializable {
    @Column(name = "orderId")
    private long orderId;

    @Column(name = "paymentId")
    private long paymentId;

    public OrderPaymentId() {
    }

    public OrderPaymentId(long orderId, long paymentId) {
        this.orderId = orderId;
        this.paymentId = paymentId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }
}
