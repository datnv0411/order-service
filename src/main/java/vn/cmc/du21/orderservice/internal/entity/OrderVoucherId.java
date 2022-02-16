package vn.cmc.du21.orderservice.internal.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderVoucherId implements Serializable {
    @Column(name = "orderId")
    private long orderId;

    @Column(name = "voucherId")
    private long voucherId;

    public OrderVoucherId() {
    }

    public OrderVoucherId(long orderId, long voucherId) {
        this.orderId = orderId;
        this.voucherId = voucherId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }
}
