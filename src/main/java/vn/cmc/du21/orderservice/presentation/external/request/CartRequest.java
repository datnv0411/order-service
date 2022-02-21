package vn.cmc.du21.orderservice.presentation.external.request;

import java.util.List;

public class CartRequest {
    private List<CartProductRequest> productRequests;
    private List<VoucherRequest> voucherRequests;

    public CartRequest() {
    }

    public CartRequest(List<CartProductRequest> productRequests, List<VoucherRequest> voucherRequests) {
        this.productRequests = productRequests;
        this.voucherRequests = voucherRequests;
    }

    public List<CartProductRequest> getProductRequests() {
        return productRequests;
    }

    public void setProductRequests(List<CartProductRequest> productRequests) {
        this.productRequests = productRequests;
    }

    public List<VoucherRequest> getVoucherRequests() {
        return voucherRequests;
    }

    public void setVoucherRequests(List<VoucherRequest> voucherRequests) {
        this.voucherRequests = voucherRequests;
    }
}
