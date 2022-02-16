package vn.cmc.du21.orderservice.persistence.internal.entity;

import javax.persistence.*;

@Entity
@Table(name = "orderproduct")
public class OrderProduct {
    @EmbeddedId
    private OrderProductId orderProductId;

    private long price;
    private int quantity;

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    public OrderProduct() {
    }

    public OrderProduct(OrderProductId orderProductId, long price, int quantity, Order order) {
        this.orderProductId = orderProductId;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
    }

    public OrderProductId getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(OrderProductId orderProductId) {
        this.orderProductId = orderProductId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
