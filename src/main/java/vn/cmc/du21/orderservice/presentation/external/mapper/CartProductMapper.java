package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.persistence.internal.entity.CartProduct;
import vn.cmc.du21.orderservice.presentation.external.response.CartProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;

public class CartProductMapper {
    private CartProductMapper()
    {
        super();
    }

    public static CartProductResponse convertCartProductToCartProductResponse(CartProduct cartProduct)
    {
        CartProductResponse cartProductResponse = new CartProductResponse();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(cartProduct.getCartProductId().getProductId());

        cartProductResponse.setProductResponse(productResponse);
        cartProductResponse.setSizeId(cartProduct.getCartProductId().getSizeId());
        cartProductResponse.setQuantity(cartProduct.getQuantity());

        return cartProductResponse;
    }
}
