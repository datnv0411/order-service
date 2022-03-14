package vn.cmc.du21.orderservice.presentation.external.response;

public class TotalCartResponse {
    private int totalProduct; // tong so san pham
    private long totalPrice; // tong gia cac san pham (gia k sale)
    private long fee; // phi phuc vu
    private long totalSale; // tong giam tien sale (cua tung san pham)
    //private long totalPriceWithSale; // tong tien san pham (gia sale)
    private long totalBeforVat; // tien truoc VAT
    private long vat; // VAT
    private long totalAfterVat; // tien sau VAT
    //private long shippingFee; // phi ship
    private long totalDiscount; // tong giam gia cua voucher (phieu giam gia)
    private long totalPaySum; // tong tien phai tra

    public TotalCartResponse() {
    }

    public TotalCartResponse(int totalProduct, long totalPrice, long fee, long totalSale, long totalBeforVat, long vat, long totalAfterVat, long totalDiscount, long totalPaySum) {
        this.totalProduct = totalProduct;
        this.totalPrice = totalPrice;
        this.fee = fee;
        this.totalSale = totalSale;
        this.totalBeforVat = totalBeforVat;
        this.vat = vat;
        this.totalAfterVat = totalAfterVat;
        this.totalDiscount = totalDiscount;
        this.totalPaySum = totalPaySum;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(long totalSale) {
        this.totalSale = totalSale;
    }

    public long getTotalBeforVat() {
        return totalBeforVat;
    }

    public void setTotalBeforVat(long totalBeforVat) {
        this.totalBeforVat = totalBeforVat;
    }

    public long getVat() {
        return vat;
    }

    public void setVat(long vat) {
        this.vat = vat;
    }

    public long getTotalAfterVat() {
        return totalAfterVat;
    }

    public void setTotalAfterVat(long totalAfterVat) {
        this.totalAfterVat = totalAfterVat;
    }

    public long getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(long totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public long getTotalPaySum() {
        return totalPaySum;
    }

    public void setTotalPaySum(long totalPaySum) {
        this.totalPaySum = totalPaySum;
    }
}
