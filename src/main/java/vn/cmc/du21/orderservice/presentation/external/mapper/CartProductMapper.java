package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.persistence.internal.entity.Cart;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProduct;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProductId;
import vn.cmc.du21.orderservice.presentation.external.request.CartProductRequest;
import vn.cmc.du21.orderservice.presentation.external.response.CartProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.SizeResponse;

import java.util.List;

public class CartProductMapper {
    private static long price;
    private static long priceSale;

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
    public static CartProductResponse convertCartProductToCartProductResponse(CartProduct cartProduct, ProductResponse productResponse){
        List<SizeResponse> listSize = productResponse.getSizeResponseList();

        for (SizeResponse item : listSize){
            if (item.getSizeId()==cartProduct.getCartProductId().getSizeId()){
                price = item.getPrice();
                priceSale = item.getPriceSale();
            }
        }
        return new CartProductResponse(productResponse.getProductId(), productResponse.getProductName()
                                        , productResponse.getQuantitative(), productResponse.getDescription()
                                        , productResponse.getCreateTime(), productResponse.getProductImage()
                                        , productResponse.getCategoryId(), productResponse.getCategoryName()
                                        , cartProduct.getCartProductId().getSizeId(), cartProduct.getQuantity()
                                        , price*cartProduct.getQuantity(),priceSale*cartProduct.getQuantity());
    }
}
