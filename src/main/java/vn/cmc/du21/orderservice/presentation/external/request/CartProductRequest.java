package vn.cmc.du21.orderservice.presentation.external.request;

public class CartProductRequest {
    private long productId;
    private long sizeId;
    private long quantity;

    public CartProductRequest() {
    }

    public CartProductRequest(long productId, long sizeId, long quantity) {
        this.productId = productId;
        this.sizeId = sizeId;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getSizeId() {
        return sizeId;
    }

    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
