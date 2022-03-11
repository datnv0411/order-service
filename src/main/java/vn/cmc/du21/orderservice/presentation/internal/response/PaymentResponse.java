package vn.cmc.du21.orderservice.presentation.internal.response;

public class PaymentResponse {
    private long paymentId;
    private long orderId;
    private long totalPaid;
    private String paymentName;
    private String status;

    public PaymentResponse() {
    }

    public PaymentResponse(long paymentId, long orderId, long totalPaid, String paymentName, String status) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.totalPaid = totalPaid;
        this.paymentName = paymentName;
        this.status = status;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(long totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


