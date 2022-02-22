package vn.cmc.du21.orderservice.presentation.external.request;

public class CartProductRequest {
    private long cartId;
    private long productId;
    private long sizeId;
    private int quantity;

    public CartProductRequest() {
    }


    public CartProductRequest(long cartId, long productId, long sizeId, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.sizeId = sizeId;
        this.quantity = quantity;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
