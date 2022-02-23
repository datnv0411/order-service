package vn.cmc.du21.orderservice.presentation.external.response;

public class VoucherUserResponse {
    private int usedTimes;
    private VoucherResponse voucher;


    public VoucherUserResponse() {
    }


    public VoucherUserResponse(int usedTimes, VoucherResponse voucherResponse) {
        this.usedTimes = usedTimes;
        this.voucher = voucherResponse;
    }

    public int getUsedTimes() {
        return usedTimes;
    }

    public void setUsedTimes(int usedTimes) {
        this.usedTimes = usedTimes;
    }

    public VoucherResponse getVoucherResponse() {
        return voucher;
    }

    public void setVoucherResponse(VoucherResponse voucherResponse) {
        this.voucher = voucherResponse;
    }
}
