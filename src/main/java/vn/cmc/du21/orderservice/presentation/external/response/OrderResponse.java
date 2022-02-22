package vn.cmc.du21.orderservice.presentation.external.response;

import vn.cmc.du21.orderservice.persistence.internal.entity.Order;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderPayment;
import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;
import vn.cmc.du21.orderservice.presentation.internal.response.AddressResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.PaymentResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.UserResponse;

import java.sql.Timestamp;
import java.util.List;

public class OrderResponse {
    // order
    private long orderId;
    private long userId;
    private long addressId;
    private String statusOrder;
    private String note;
    private Timestamp createTime;
    private Timestamp holdTime;
    private Timestamp deliveryTime;

    // voucher response
    private List<VoucherResponse> voucherResponses;

    // product response
    private List<OrderProductResponse> productResponses;

    // payment response
    private OrderPaymentResponse paymentResponse;

    // address response
    private AddressResponse addressResponse;

    // total response
    private TotalResponse totalResponse;

    public OrderResponse() {
    }

    public OrderResponse(long orderId, long userId, long addressId, String statusOrder, String note, Timestamp createTime, Timestamp holdTime, Timestamp deliveryTime, List<VoucherResponse> voucherResponses, List<OrderProductResponse> productResponses, OrderPaymentResponse paymentResponse, AddressResponse addressResponse, TotalResponse totalResponse) {
        this.orderId = orderId;
        this.userId = userId;
        this.addressId = addressId;
        this.statusOrder = statusOrder;
        this.note = note;
        this.createTime = createTime;
        this.holdTime = holdTime;
        this.deliveryTime = deliveryTime;
        this.voucherResponses = voucherResponses;
        this.productResponses = productResponses;
        this.paymentResponse = paymentResponse;
        this.addressResponse = addressResponse;
        this.totalResponse = totalResponse;
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

    public List<VoucherResponse> getVoucherResponses() {
        return voucherResponses;
    }

    public void setVoucherResponses(List<VoucherResponse> voucherResponses) {
        this.voucherResponses = voucherResponses;
    }

    public List<OrderProductResponse> getProductResponses() {
        return productResponses;
    }

    public void setProductResponses(List<OrderProductResponse> productResponses) {
        this.productResponses = productResponses;
    }

    public OrderPaymentResponse getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(OrderPaymentResponse paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

    public AddressResponse getAddressResponse() {
        return addressResponse;
    }

    public void setAddressResponse(AddressResponse addressResponse) {
        this.addressResponse = addressResponse;
    }

    public TotalResponse getTotalResponse() {
        return totalResponse;
    }

    public void setTotalResponse(TotalResponse totalResponse) {
        this.totalResponse = totalResponse;
    }
}