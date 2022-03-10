package vn.cmc.du21.orderservice.presentation.external.request;

import java.sql.Timestamp;
import java.util.List;

public class OrderRequest {
    private long userId;
    private long addressId;
    private long paymentId;
    private String statusOrder;
    private String note;
    private String createTime;
    private String holdTime;
    private String deliveryTime;

    // voucher response
    private List<VoucherRequest> voucherRequests;

    // product response
    private List<OrderProductRequest> orderProductRequests;


    public OrderRequest() {
    }

    public OrderRequest(long userId, long addressId, long paymentId, String statusOrder, String note, String createTime, String holdTime, String deliveryTime, List<VoucherRequest> voucherRequests, List<OrderProductRequest> orderProductRequests) {
        this.userId = userId;
        this.addressId = addressId;
        this.paymentId = paymentId;
        this.statusOrder = statusOrder;
        this.note = note;
        this.createTime = createTime;
        this.holdTime = holdTime;
        this.deliveryTime = deliveryTime;
        this.voucherRequests = voucherRequests;
        this.orderProductRequests = orderProductRequests;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(String holdTime) {
        this.holdTime = holdTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public List<VoucherRequest> getVoucherRequests() {
        return voucherRequests;
    }

    public void setVoucherRequests(List<VoucherRequest> voucherRequests) {
        this.voucherRequests = voucherRequests;
    }

    public List<OrderProductRequest> getOrderProductRequests() {
        return orderProductRequests;
    }

    public void setOrderProductRequests(List<OrderProductRequest> orderProductRequests) {
        this.orderProductRequests = orderProductRequests;
    }
}
