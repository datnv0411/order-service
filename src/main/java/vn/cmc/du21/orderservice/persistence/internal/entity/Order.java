package vn.cmc.du21.orderservice.persistence.internal.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "ordered")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    private long userId;
    private long paymentId;
    private String statusOrder;
    private String note;
    private Timestamp createTime;
    private Timestamp holdTime;
    private Timestamp deliveryTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    @ManyToMany
    @JoinTable(name = "ordervoucher", joinColumns = @JoinColumn(name = "orderId"), inverseJoinColumns = @JoinColumn(name = "voucherId"))
    private List<Voucher> vouchers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deliveryAddressId")
    private DeliveryAddress deliveryAddress;

    public Order() {
    }

    public Order(long orderId, long userId, long paymentId, String statusOrder, String note, Timestamp createTime, Timestamp holdTime, Timestamp deliveryTime, List<OrderProduct> orderProducts, List<Voucher> vouchers, DeliveryAddress deliveryAddress) {
        this.orderId = orderId;
        this.userId = userId;
        this.paymentId = paymentId;
        this.statusOrder = statusOrder;
        this.note = note;
        this.createTime = createTime;
        this.holdTime = holdTime;
        this.deliveryTime = deliveryTime;
        this.orderProducts = orderProducts;
        this.vouchers = vouchers;
        this.deliveryAddress = deliveryAddress;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(Timestamp holdTime) {
        this.holdTime = holdTime;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
