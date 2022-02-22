package vn.cmc.du21.orderservice.presentation.internal.request;

public class SizeRequest {
    private long sizeId;
    private String sizeName;
    private long price;
    private long priceSale;
    private boolean sizeDefault;

    public SizeRequest() {
    }

    public SizeRequest(long sizeId, String sizeName, long price, long priceSale, boolean sizeDefault) {
        this.sizeId = sizeId;
        this.sizeName = sizeName;
        this.price = price;
        this.priceSale = priceSale;
        this.sizeDefault = sizeDefault;
    }

    public long getSizeId() {
        return sizeId;
    }

    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(long priceSale) {
        this.priceSale = priceSale;
    }

    public boolean isSizeDefault() {
        return sizeDefault;
    }

    public void setSizeDefault(boolean sizeDefault) {
        this.sizeDefault = sizeDefault;
    }
}
