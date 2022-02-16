package vn.cmc.du21.orderservice.persistence.internal.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "ordered")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    private long userId;
    private long addressId;
    private String statusOrder;
    private Timestamp createTime;
    private Timestamp deliveryTime;
    private String note;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderPayment> orderPayments;

    @ManyToMany
    @JoinTable(name = "ordervoucher", joinColumns = @JoinColumn(name = "orderId"), inverseJoinColumns = @JoinColumn(name = "voucherId"))
    private List<Voucher> vouchers;

    public Order() {
    }

    public Order(long orderId, long userId, long addressId, String statusOrder, Timestamp createTime, Timestamp deliveryTime, String note, List<OrderProduct> orderProducts, List<OrderPayment> orderPayments, List<Voucher> vouchers) {
        this.orderId = orderId;
        this.userId = userId;
        this.addressId = addressId;
        this.statusOrder = statusOrder;
        this.createTime = createTime;
        this.deliveryTime = deliveryTime;
        this.note = note;
        this.orderProducts = orderProducts;
        this.orderPayments = orderPayments;
        this.vouchers = vouchers;
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

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public List<OrderPayment> getOrderPayments() {
        return orderPayments;
    }

    public void setOrderPayments(List<OrderPayment> orderPayments) {
        this.orderPayments = orderPayments;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }
}
