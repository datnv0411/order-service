package vn.cmc.du21.orderservice.presentation.internal.response;

public class PaymentResponse {
    private long paymentId;

    public PaymentResponse() {
    }

    public PaymentResponse(long paymentId) {
        this.paymentId = paymentId;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }
}
