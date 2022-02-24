package vn.cmc.du21.orderservice.presentation.external.response;

public class TotalCartResponse {
    private int totalProduct; // tong so san pham
    private long totalPrice; // tong gia cac san pham (gia k sale)
    private long totalSale; // tong giam tien sale (cua tung san pham)
    private long shippingFee; // phi ship
    private long totalDiscount; // tong giam gia (phieu giam gia)
    private long totalPaySum; // tong tien phai tra

    public TotalCartResponse() {
    }

    public TotalCartResponse(int totalProduct, long totalPrice, long totalSale, long shippingFee, long totalDiscount, long totalPaySum) {
        this.totalProduct = totalProduct;
        this.totalPrice = totalPrice;
        this.totalSale = totalSale;
        this.shippingFee = shippingFee;
        this.totalDiscount = totalDiscount;
        this.totalPaySum = totalPaySum;
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

    public long getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(long shippingFee) {
        this.shippingFee = shippingFee;
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
