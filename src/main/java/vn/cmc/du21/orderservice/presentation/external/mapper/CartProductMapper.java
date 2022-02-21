package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.persistence.internal.entity.Cart;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProduct;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProductId;
import vn.cmc.du21.orderservice.presentation.external.request.CartProductRequest;

public class CartProductMapper {
    private CartProductMapper()
    {
        super();
    }
    public static CartProduct convertCartProductRequestToCartProduct(CartProductRequest cartProductRequest, Cart cart){
        CartProductId cartProductId = new CartProductId();
        cartProductId.setCartId(cartProductRequest.getCartId());
        cartProductId.setProductId(cartProductRequest.getProductId());
        cartProductId.setSizeId(cartProductRequest.getSizeId());
        return new CartProduct(cartProductId,cartProductRequest.getQuantity(),cart);
    }
}
