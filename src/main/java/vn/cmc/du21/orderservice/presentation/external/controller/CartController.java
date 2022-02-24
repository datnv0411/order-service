package vn.cmc.du21.orderservice.presentation.external.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.orderservice.common.JwtTokenProvider;
import vn.cmc.du21.orderservice.common.restful.StandardResponse;
import vn.cmc.du21.orderservice.common.restful.StatusResponse;
import vn.cmc.du21.orderservice.presentation.external.mapper.CartProductMapper;
import vn.cmc.du21.orderservice.presentation.external.mapper.VoucherMapper;
import vn.cmc.du21.orderservice.presentation.external.request.CartProductRequest;
import vn.cmc.du21.orderservice.presentation.external.request.CartRequest;
import vn.cmc.du21.orderservice.presentation.external.response.CartProductResponse;
import vn.cmc.du21.orderservice.presentation.external.response.CartResponse;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.SizeResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.UserResponse;
import vn.cmc.du21.orderservice.service.CartService;
import vn.cmc.du21.orderservice.service.VoucherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "api/v1.0/cart")
public class CartController {
    @Autowired
    private Environment env;
    @Autowired
    CartService cartService;
    @Autowired
    VoucherService voucherService;

    private static final String GET_DETAIL_PRODUCT = "/api/v1.0/product/get-detail-product/";
    private static final String PATH_INVENTORY_SERVICE = "path.inventory-service";

    @GetMapping(path = "/my-cart")
    ResponseEntity<Object> cartDetail(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        log.info("Mapped cartDetail method {{GET: /cart/my-cart}}");
        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        long userId = userLogin.getUserId();

        CartResponse cartResponse = new CartResponse();

        //get list product
        List<CartProductResponse> cartProductResponseList = cartService.getMyCart(userId).getCartProducts()
                .stream()
                .map(CartProductMapper::convertCartProductToCartProductResponse)
                .collect(Collectors.toList());

        for (CartProductResponse item : cartProductResponseList) {
            final String uri = env.getProperty(PATH_INVENTORY_SERVICE) + GET_DETAIL_PRODUCT +
                    item.getProductResponse().getProductId();

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<StandardResponse<ProductResponse>> requestItem = new HttpEntity<>(new StandardResponse<>());

            ResponseEntity<StandardResponse<ProductResponse>> responseItem =
                    restTemplate.exchange(
                            uri, HttpMethod.GET, requestItem,
                            new ParameterizedTypeReference<StandardResponse<ProductResponse>>() {}
                    );

            StandardResponse<ProductResponse> productResponse = responseItem.getBody();

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

    @PutMapping("/update")
    ResponseEntity<Object> updateCart(@RequestBody CartRequest cartRequest,
                                      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("Mapped updateCart method {{GET: /cart/update}}");
        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        long userId = userLogin.getUserId();

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
            final String uri = env.getProperty(PATH_INVENTORY_SERVICE) + GET_DETAIL_PRODUCT +
                    item.getProductResponse().getProductId();

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<StandardResponse<ProductResponse>> requestItem = new HttpEntity<>(new StandardResponse<>());
            ResponseEntity<StandardResponse<ProductResponse>> responseItem = restTemplate
                    .exchange(uri, HttpMethod.GET, requestItem, new ParameterizedTypeReference<StandardResponse<ProductResponse>>() {
                    });

            StandardResponse<ProductResponse> productResponse = responseItem.getBody();

            item.setProductResponse(productResponse.getData());
        }

        cartResponse.setItems(cartProductResponseList);

        List<VoucherResponse> voucherResponseList = voucherService.applyVoucher(
                cartRequest.getVoucherRequests()
                        .stream()
                        .map(VoucherMapper::convertVoucherRequestToVoucher)
                        .collect(Collectors.toList()),
                cartResponse.getTotals().getTotalPrice() - cartResponse.getTotals().getTotalSale(),
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

    @GetMapping("/add")
    ResponseEntity<Object> addProduct (@RequestBody CartProductRequest cartProductRequest
                                        , HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("Mapped addProduct method {{GET: /add}}");

        if (cartProductRequest.getQuantity() == 0) cartProductRequest.setQuantity(1);

        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        long userId = userLogin.getUserId();

        try{
            final String uri = env.getProperty(PATH_INVENTORY_SERVICE) + GET_DETAIL_PRODUCT + cartProductRequest.getProductId();

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<StandardResponse<ProductResponse>> requestProduct = new HttpEntity<>(new StandardResponse<>());
            ResponseEntity<StandardResponse<ProductResponse>> responseProduct = restTemplate
                    .exchange(uri, HttpMethod.GET, requestProduct, new ParameterizedTypeReference<StandardResponse<ProductResponse>>() {
                    });

            StandardResponse<ProductResponse> productResponse = responseProduct.getBody();

            List<SizeResponse> listSize = productResponse.getData().getSizeResponseList();
            if(cartProductRequest.getSizeId() == 0){
                for (SizeResponse item : listSize){
                    if (item.isSizeDefault()) cartProductRequest.setSizeId(item.getSizeId());
                }
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
        cartService.addProduct(
                CartProductMapper.convertCartProductRequestToCartProduct(cartProductRequest,cartService.findCart(userId))
        );

        List<CartProductResponse> cartProductResponses =
                cartService.findAllByCartId(cartProductRequest.getCartId())
                .stream()
                .map(CartProductMapper::convertCartProductToCartProductResponse)
                .collect(Collectors.toList());
        for (CartProductResponse item : cartProductResponses){
            final String uri = env.getProperty(PATH_INVENTORY_SERVICE) + GET_DETAIL_PRODUCT +
                    item.getProductResponse().getProductId();

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<StandardResponse<ProductResponse>> requestProduct = new HttpEntity<>(new StandardResponse<>());
            ResponseEntity<StandardResponse<ProductResponse>> responseProduct = restTemplate
                    .exchange(uri, HttpMethod.GET, requestProduct, new ParameterizedTypeReference<StandardResponse<ProductResponse>>() {
                    });

            ProductResponse productResponse = responseProduct.getBody().getData();

            item.setProductResponse(productResponse);
        }

        CartResponse cartResponse = new CartResponse();
        cartResponse.setItems(cartProductResponses);

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Added",
                        cartResponse
                )
        );
    }

    @DeleteMapping("/remove")
    ResponseEntity<Object> removeProduct(@RequestParam(name = "productId", required = true) long productId
                                        , @RequestParam(name = "sizeId", required = true) long sizeId
                                        , HttpServletRequest request, HttpServletResponse response) throws Throwable{

        log.info("Mapped removeProduct method {{DELETE: /remove}}");
        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        long userId = userLogin.getUserId();

        cartService.removeProduct(cartService.getMyCart(userId).getCartId(), productId, sizeId);

        List<CartProductResponse> cartProductResponseList = cartService.getMyCart(userId).getCartProducts()
                .stream()
                .map(CartProductMapper::convertCartProductToCartProductResponse)
                .collect(Collectors.toList());

        for (CartProductResponse item : cartProductResponseList){
            final String uri = env.getProperty(PATH_INVENTORY_SERVICE) + GET_DETAIL_PRODUCT +
                    item.getProductResponse().getProductId();

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<StandardResponse<ProductResponse>> requestProduct = new HttpEntity<>(new StandardResponse<>());
            ResponseEntity<StandardResponse<ProductResponse>> responseProduct = restTemplate
                    .exchange(uri, HttpMethod.GET, requestProduct, new ParameterizedTypeReference<StandardResponse<ProductResponse>>() {
                    });

            ProductResponse productResponse = responseProduct.getBody().getData();

            item.setProductResponse(productResponse);
        }

        CartResponse cartResponse = new CartResponse();
        cartResponse.setItems(cartProductResponseList);

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Deleted",
                        cartResponse
                )
        );
    }

    @DeleteMapping("/quick-remove")
    ResponseEntity<Object> quickRemove(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("Mapped removeProduct method {{DELETE: /quick-remove}}");
        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        long userId = userLogin.getUserId();

        long cartId = cartService.findCart(userId).getCartId();
        cartService.removeAll(cartId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Deleted all"

                )
        );
    }
}
