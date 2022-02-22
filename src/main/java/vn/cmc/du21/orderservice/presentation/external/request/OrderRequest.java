package vn.cmc.du21.orderservice.presentation.external.request;

import vn.cmc.du21.orderservice.presentation.external.response.TotalOrderResponse;
import vn.cmc.du21.orderservice.presentation.internal.request.AddressRequest;

import java.sql.Timestamp;
import java.util.List;

public class OrderRequest {
    private long userId;
    private long addressId;
    private String statusOrder;
    private String note;
    private Timestamp createTime;
    private Timestamp holdTime;
    private Timestamp deliveryTime;
    // voucher response
    private List<VoucherRequest> voucherRequests;

    // product response
    private List<OrderProductRequest> productRequests;

    // payment response
    private OrderPaymentRequest paymentResponse;

    // address response
    private AddressRequest addressResponse;

    // total response
    private TotalOrderResponse totalResponse;

    public OrderRequest() {
    }

    public OrderRequest(long userId, long addressId, String statusOrder, String note, Timestamp createTime, Timestamp holdTime, Timestamp deliveryTime, List<VoucherRequest> voucherRequests, List<OrderProductRequest> productRequests, OrderPaymentRequest paymentResponse, AddressRequest addressResponse, TotalOrderResponse totalResponse) {
        this.userId = userId;
        this.addressId = addressId;
        this.statusOrder = statusOrder;
        this.note = note;
        this.createTime = createTime;
        this.holdTime = holdTime;
        this.deliveryTime = deliveryTime;
        this.voucherRequests = voucherRequests;
        this.productRequests = productRequests;
        this.paymentResponse = paymentResponse;
        this.addressResponse = addressResponse;
        this.totalResponse = totalResponse;
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

    public List<VoucherRequest> getVoucherRequests() {
        return voucherRequests;
    }

    public void setVoucherRequests(List<VoucherRequest> voucherRequests) {
        this.voucherRequests = voucherRequests;
    }

    public List<OrderProductRequest> getProductRequests() {
        return productRequests;
    }

    public void setProductRequests(List<OrderProductRequest> productRequests) {
        this.productRequests = productRequests;
    }

    public OrderPaymentRequest getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(OrderPaymentRequest paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

    public AddressRequest getAddressResponse() {
        return addressResponse;
    }

    public void setAddressResponse(AddressRequest addressResponse) {
        this.addressResponse = addressResponse;
    }

    public TotalOrderResponse getTotalResponse() {
        return totalResponse;
    }

    public void setTotalResponse(TotalOrderResponse totalResponse) {
        this.totalResponse = totalResponse;
    }
}
