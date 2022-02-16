package vn.cmc.du21.orderservice.persistence.internal.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cartId;
    private long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartProduct> cartProducts;

    public Cart() {
    }

    public Cart(long cartId, long userId, List<CartProduct> cartProducts) {
        this.cartId = cartId;
        this.userId = userId;
        this.cartProducts = cartProducts;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }
}
