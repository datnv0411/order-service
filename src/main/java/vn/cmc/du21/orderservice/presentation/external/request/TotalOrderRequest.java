package vn.cmc.du21.orderservice.presentation.external.request;

public class TotalOrderRequest {
    private long totalPrice; // tổng tiền tạm tính
    private long totalDiscount; // tổng tiền giảm
    private long shippingFee; // phí ship
    private long totalBeforeVAT; // tổng tiền trước VAT
    private long totalVAT; // tổng tiền VAT
    private long totalAfterVAT; // tổng tiền sau VAT
    private long totalVoucherDiscount;
    private long totalOrder; // tổng tiền phải trả

    public TotalOrderRequest() {
    }

    public TotalOrderRequest(long totalPrice, long totalDiscount, long shippingFee, long totalBeforeVAT, long totalVAT, long totalAfterVAT, long totalVoucherDiscount, long totalOrder) {
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.shippingFee = shippingFee;
        this.totalBeforeVAT = totalBeforeVAT;
        this.totalVAT = totalVAT;
        this.totalAfterVAT = totalAfterVAT;
        this.totalVoucherDiscount = totalVoucherDiscount;
        this.totalOrder = totalOrder;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(long totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public long getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(long shippingFee) {
        this.shippingFee = shippingFee;
    }

    public long getTotalBeforeVAT() {
        return totalBeforeVAT;
    }

    public void setTotalBeforeVAT(long totalBeforeVAT) {
        this.totalBeforeVAT = totalBeforeVAT;
    }

    public long getTotalVAT() {
        return totalVAT;
    }

    public void setTotalVAT(long totalVAT) {
        this.totalVAT = totalVAT;
    }

    public long getTotalAfterVAT() {
        return totalAfterVAT;
    }

    public void setTotalAfterVAT(long totalAfterVAT) {
        this.totalAfterVAT = totalAfterVAT;
    }

    public long getTotalVoucherDiscount() {
        return totalVoucherDiscount;
    }

    public void setTotalVoucherDiscount(long totalVoucherDiscount) {
        this.totalVoucherDiscount = totalVoucherDiscount;
    }

    public long getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(long totalOrder) {
        this.totalOrder = totalOrder;
    }
}
