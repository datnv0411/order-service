package vn.cmc.du21.orderservice.persistence.internal.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CartProductId implements Serializable {

    @Column(name = "cartId")
    private long cartId;

    @Column(name = "productId")
    private long productId;

    @Column(name = "sizeId")
    private long sizeId;

    public CartProductId() {
    }

    public CartProductId(long cartId, long productId, long sizeId) {
        this.cartId = cartId;
        this.productId = productId;
        this.sizeId = sizeId;
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
}
