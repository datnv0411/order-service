package vn.cmc.du21.orderservice.presentation.external.request;

public class VoucherRequest {
    private long voucherId;

    public VoucherRequest() {
    }

    public VoucherRequest(long voucherId) {
        this.voucherId = voucherId;
    }

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }
}
