package vn.cmc.du21.orderservice.presentation.external.request;

public class VoucherRequest {
    private long voucherCode;

    public VoucherRequest() {
    }

    public VoucherRequest(long voucherCode) {
        this.voucherCode = voucherCode;
    }

    public long getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(long voucherCode) {
        this.voucherCode = voucherCode;
    }
}
