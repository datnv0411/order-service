package vn.cmc.du21.orderservice.presentation.external.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.orderservice.common.restful.StandardResponse;
import vn.cmc.du21.orderservice.common.restful.StatusResponse;
import vn.cmc.du21.orderservice.presentation.external.mapper.CartProductMapper;
import vn.cmc.du21.orderservice.presentation.external.mapper.VoucherMapper;
import vn.cmc.du21.orderservice.presentation.external.request.CartRequest;
import vn.cmc.du21.orderservice.presentation.external.response.CartProductResponse;
import vn.cmc.du21.orderservice.presentation.external.response.CartResponse;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;
import vn.cmc.du21.orderservice.service.CartService;
import vn.cmc.du21.orderservice.service.VoucherService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1.0")
public class CartController {
    @Autowired
    private Environment env;
    @Autowired
    CartService cartService;
    @Autowired
    VoucherService voucherService;

    @GetMapping(path = "/cart/my-cart")
    ResponseEntity<Object> cartDetail()
    {
        long userId = 1L;

        CartResponse cartResponse = new CartResponse();

        //get list product
        List<CartProductResponse> cartProductResponseList = cartService.getMyCart(userId).getCartProducts()
                .stream()
                .map(CartProductMapper::convertCartProductToCartProductResponse)
                .collect(Collectors.toList());

        for (CartProductResponse item : cartProductResponseList) {
            final String uri = env.getProperty("path.inventory-service") + "/api/v1.0/product/get-detail-product/" +
                    item.getProductResponse().getProductId();

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<StandardResponse<ProductResponse>> request = new HttpEntity<>(new StandardResponse<ProductResponse>());
            ResponseEntity<StandardResponse<ProductResponse>> response = restTemplate
                    .exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<StandardResponse<ProductResponse>>() {
                    });

            StandardResponse<ProductResponse> productResponse = response.getBody();

            item.setProductResponse(productResponse.getData());
        }

        cartResponse.setItems(cartProductResponseList);

        return ResponseEntity.ok().body(
          new StandardResponse<>(
                  StatusResponse.SUCCESSFUL,
                  "Get cart successfully !!!",
                  cartResponse
          )
        );
    }

    @PutMapping("/cart/update")
    ResponseEntity<Object> updateCart(@RequestBody CartRequest cartRequest)
    {
        long userId = 1L;
        // update cart - save database
        cartService.updateProductOnCart(
                cartRequest.getProductRequests()
                        .stream()
                        .map(CartProductMapper::convertCartProductRequestToCartProduct)
                        .collect(Collectors.toList()),
                userId
        );

        CartResponse cartResponse = new CartResponse();
        //get list product
        List<CartProductResponse> cartProductResponseList = cartService.getMyCart(userId).getCartProducts()
                .stream()
                .map(CartProductMapper::convertCartProductToCartProductResponse)
                .collect(Collectors.toList());

        for (CartProductResponse item : cartProductResponseList) {
            final String uri = env.getProperty("path.inventory-service") + "/api/v1.0/product/get-detail-product/" +
                    item.getProductResponse().getProductId();

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<StandardResponse<ProductResponse>> request = new HttpEntity<>(new StandardResponse<ProductResponse>());
            ResponseEntity<StandardResponse<ProductResponse>> response = restTemplate
                    .exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<StandardResponse<ProductResponse>>() {
                    });

            StandardResponse<ProductResponse> productResponse = response.getBody();

            item.setProductResponse(productResponse.getData());
        }

        cartResponse.setItems(cartProductResponseList);

        List<VoucherResponse> voucherResponseList = voucherService.applyVoucher(
                cartRequest.getVoucherRequests()
                        .stream()
                        .map(VoucherMapper::convertVoucherRequestToVoucher)
                        .collect(Collectors.toList()),
                cartResponse.getTotals().getTotalPrice(),
                userId
        ).stream().map(VoucherMapper::convertVoucherToVoucherResponse).collect(Collectors.toList());

        cartResponse.setSelectedVouchers(voucherResponseList);

        return ResponseEntity.ok().body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Update cart successfully !!!",
                        cartResponse
                )
        );
    }
}
