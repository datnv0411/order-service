package vn.cmc.du21.orderservice.presentation.external.request;

public class VoucherRequest {
    private String codeVoucher;

    public VoucherRequest() {
    }

    public VoucherRequest(String codeVoucher) {
        this.codeVoucher = codeVoucher;
    }

    public String getCodeVoucher() {
        return codeVoucher;
    }

    public void setCodeVoucher(String codeVoucher) {
        this.codeVoucher = codeVoucher;
    }
}
