package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.persistence.internal.entity.Cart;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProduct;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProductId;
import vn.cmc.du21.orderservice.presentation.external.request.CartProductRequest;
import vn.cmc.du21.orderservice.presentation.external.response.CartProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;

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

    public static CartProductResponse convertToCartProductResponse(CartProduct cartProduct, ProductResponse productResponse)
    {
        CartProductResponse cartProductResponse = new CartProductResponse();
        cartProductResponse.setProductResponse(productResponse);
        cartProductResponse.setQuantity(cartProduct.getQuantity());
        cartProductResponse.setSizeId(cartProduct.getCartProductId().getSizeId());
        cartProductResponse.setTotalPrice(cartProductResponse.getTotalPrice());

        return cartProductResponse;
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

    public static CartProduct convertCartProductRequestToCartProduct(CartProductRequest cartProductRequest)
    {
        CartProduct cartProduct = new CartProduct();
        CartProductId cartProductId = new CartProductId();

        cartProductId.setProductId(cartProductRequest.getProductId());
        cartProductId.setSizeId(cartProductRequest.getSizeId());

        cartProduct.setCartProductId(cartProductId);
        cartProduct.setQuantity(cartProductRequest.getQuantity());

        return cartProduct;
    }
}
