package vn.cmc.du21.orderservice.persistence.internal.entity;

import javax.persistence.*;

@Entity
@Table(name = "cartproduct")
public class CartProduct {
    @EmbeddedId
    private CartProductId cartProductId;

    private int quantity;

    @ManyToOne
    @MapsId("cartId")
    private Cart cart;

    public CartProduct() {
    }

    public CartProduct(CartProductId cartProductId, int quantity, Cart cart) {
        this.cartProductId = cartProductId;
        this.quantity = quantity;
        this.cart = cart;
    }

    public CartProductId getCartProductId() {
        return cartProductId;
    }

    public void setCartProductId(CartProductId cartProductId) {
        this.cartProductId = cartProductId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
