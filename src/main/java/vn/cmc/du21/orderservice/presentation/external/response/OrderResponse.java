package vn.cmc.du21.orderservice.presentation.external.response;

import vn.cmc.du21.orderservice.presentation.internal.response.PaymentResponse;

import java.util.List;

public class OrderResponse {
    // order
    private long orderId;
    private long userId;
    private String statusOrder;
    private String note;
    private String createTime;
    private String holdTime;
    private String deliveryTime;

    // voucher response
    private List<VoucherResponse> voucherResponses;

    // product response
    private List<OrderProductResponse> productResponses;

    // payment response
    private PaymentResponse paymentResponse;

    // address response
    private DeliveryAddressResponse addressResponse;

    // total response
    private TotalOrderResponse totalResponse;

    public OrderResponse() {
    }

    public OrderResponse(long orderId, long userId, String statusOrder, String note, String createTime, String holdTime, String deliveryTime, List<VoucherResponse> voucherResponses, List<OrderProductResponse> productResponses, PaymentResponse paymentResponse, DeliveryAddressResponse addressResponse, TotalOrderResponse totalResponse) {
        this.orderId = orderId;
        this.userId = userId;
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

    public OrderResponse(long orderId, long userId, String statusOrder, String createTime, List<OrderProductResponse> productResponses, TotalOrderResponse totalResponse) {
        this.orderId = orderId;
        this.userId = userId;
        this.statusOrder = statusOrder;
        this.createTime = createTime;
        this.productResponses = productResponses;
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

    public PaymentResponse getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(PaymentResponse paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

    public DeliveryAddressResponse getAddressResponse() {
        return addressResponse;
    }

    public void setAddressResponse(DeliveryAddressResponse addressResponse) {
        this.addressResponse = addressResponse;
    }

    public TotalOrderResponse getTotalResponse() {
        return totalResponse;
    }

    public void setTotalResponse(TotalOrderResponse totalResponse) {
        this.totalResponse = totalResponse;
    }
}