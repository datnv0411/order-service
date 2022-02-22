package vn.cmc.du21.orderservice.presentation.external.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.orderservice.common.APIGetDetailProduct;
import vn.cmc.du21.orderservice.common.restful.StandardResponse;
import vn.cmc.du21.orderservice.common.restful.StatusResponse;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProduct;
import vn.cmc.du21.orderservice.persistence.internal.entity.CartProductId;
import vn.cmc.du21.orderservice.presentation.external.mapper.CartProductMapper;
import vn.cmc.du21.orderservice.presentation.external.request.CartProductRequest;
import vn.cmc.du21.orderservice.presentation.external.response.CartProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.SizeResponse;
import vn.cmc.du21.orderservice.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/api/v1.0/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/add")
    ResponseEntity<Object> addProduct (@RequestBody CartProductRequest cartProductRequest
                                        , HttpServletRequest request, HttpServletResponse response) {
        log.info("Mapped addProduct method {{GET: /add}}");
        if (cartProductRequest.getQuantity() == 0) cartProductRequest.setQuantity(1);
//        UserResponse userLogin;
//        try {
//            userLogin = JwtTokenProvider.getInfoUserFromToken(request);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
//                    new StandardResponse<>(
//                            StatusResponse.UNAUTHORIZED,
//                            "Bad token!!!"
//                    )
//            );
//        }
//        long userId = userLogin.getUserId();
        long userId = 8;
        //check cart exist
        if(cartService.findCart(userId)==null){
            cartService.createCart(userId);
        }
        try{
            StandardResponse<ProductResponse> productResponse =
                    APIGetDetailProduct.getDetailProduct(request, cartProductRequest.getProductId());
            List<SizeResponse> listSize = productResponse.getData().getSizeResponseList();
            if(cartProductRequest.getSizeId() == 0){
                for (SizeResponse item : listSize){
                    if (item.isSizeDefault()) cartProductRequest.setSizeId(item.getSizeId());
                }
            }
            List<Long> listSizeId = new ArrayList<>();
            for (SizeResponse item : listSize){
                listSizeId.add(item.getSizeId());
            }
            if (!listSizeId.contains(cartProductRequest.getSizeId())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new StandardResponse<>(
                                StatusResponse.NOT_FOUND,
                                "Size not found",""
                        )
                );
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new StandardResponse<>(
                            StatusResponse.NOT_FOUND,
                            "Product not found",""
                    )
            );
        }
        cartProductRequest.setCartId(cartService.findCart(userId).getCartId());
        cartService.addProduct(CartProductMapper.convertCartProductRequestToCartProduct(cartProductRequest,cartService.findCart(userId)));

        List<CartProductResponse> listResponse = new ArrayList<>();
        List<CartProduct> list = cartService.findAllByCartId(cartProductRequest.getCartId());
        for (CartProduct item : list){
            ProductResponse productResponse =
                    APIGetDetailProduct.getDetailProduct(request, item.getCartProductId().getProductId()).getData();
            listResponse.add(CartProductMapper.convertCartProductToCartProductResponse(item,productResponse));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Added",listResponse
                )
        );

    }

    @DeleteMapping("/remove/{productId}/{sizeId}")
    ResponseEntity<Object> removeProduct(@PathVariable(name = "productId", required = true) long productId
                                        , @PathVariable(name = "sizeId", required = true) long sizeId
                                        , HttpServletRequest request, HttpServletResponse response) throws Throwable{
        log.info("Mapped removeProduct method {{DELETE: /remove}}");
//        UserResponse userLogin;
//        try {
//            userLogin = JwtTokenProvider.getInfoUserFromToken(request);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
//                    new StandardResponse<>(
//                            StatusResponse.UNAUTHORIZED,
//                            "Bad token!!!"
//                    )
//            );
//        }
//        long userId = userLogin.getUserId();
        long userId = 8;
        if(cartService.findCart(userId)==null){
            cartService.createCart(userId);
        }

        CartProductId cartProductId = new CartProductId(cartService.findCart(userId).getCartId(), productId, sizeId);
        cartService.removeProduct(cartProductId);

        List<CartProductResponse> listResponse = new ArrayList<>();
        List<CartProduct> list = cartService.findAllByCartId(cartService.findCart(userId).getCartId());
        for (CartProduct item : list){
            ProductResponse productResponse =
                    APIGetDetailProduct.getDetailProduct(request, item.getCartProductId().getProductId()).getData();
            listResponse.add(CartProductMapper.convertCartProductToCartProductResponse(item,productResponse));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Deleted",listResponse
                )
        );
    }

    @DeleteMapping("/quick-remove")
    ResponseEntity<Object> quickRemove(HttpServletRequest request, HttpServletResponse response){
        log.info("Mapped removeProduct method {{DELETE: /quick-remove}}");
//        UserResponse userLogin;
//        try {
//            userLogin = JwtTokenProvider.getInfoUserFromToken(request);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
//                    new StandardResponse<>(
//                            StatusResponse.UNAUTHORIZED,
//                            "Bad token!!!"
//                    )
//            );
//        }
//        long userId = userLogin.getUserId();
        long userId = 8;
        if(cartService.findCart(userId)==null){
            cartService.createCart(userId);
        }
        long cartId = cartService.findCart(userId).getCartId();
        cartService.removeAll(cartId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Deleted all",null
                )
        );
    }
}
