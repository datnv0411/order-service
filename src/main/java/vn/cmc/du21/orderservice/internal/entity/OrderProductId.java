package vn.cmc.du21.orderservice.internal.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderProductId implements Serializable {
    @Column(name = "orderId")
    private long orderId;

    @Column(name = "productId")
    private long productId;

    @Column(name = "sizeId")
    private long sizeId;

    public OrderProductId() {
    }

    public OrderProductId(long orderId, long productId, long sizeId) {
        this.orderId = orderId;
        this.productId = productId;
        this.sizeId = sizeId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getSizeId() {
        return sizeId;
    }

    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
    }
}
