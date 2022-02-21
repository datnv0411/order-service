package vn.cmc.du21.orderservice.presentation.external.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.orderservice.common.JwtTokenProvider;
import vn.cmc.du21.orderservice.common.restful.StandardResponse;
import vn.cmc.du21.orderservice.common.restful.StatusResponse;
import vn.cmc.du21.orderservice.presentation.external.mapper.OrderMapper;
import vn.cmc.du21.orderservice.presentation.external.mapper.OrderProductMapper;
import vn.cmc.du21.orderservice.presentation.external.response.OrderProductResponse;
import vn.cmc.du21.orderservice.presentation.external.response.OrderResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.AddressResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.UserResponse;
import vn.cmc.du21.orderservice.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(path = "/api/v1.0")
public class OrderController {
    @Autowired
    OrderService orderService;

    // get detail address
    public static AddressResponse getDetailAddress(long addressId,
                                                   HttpServletRequest request, HttpServletResponse response){

        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(request.getHeader("Authorization").split(" ")[1]);
        String uri = "http://192.168.66.125:8100/api/v1.0/address/" + addressId + "";

        HttpEntity<StandardResponse<AddressResponse>> entity = new HttpEntity<>(new StandardResponse<AddressResponse>(), httpHeaders);
        ResponseEntity<StandardResponse<AddressResponse>> res = restTemplate
                .exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<StandardResponse<AddressResponse>>() {});

        AddressResponse addressResponse = res.getBody().getData();

        return addressResponse;
    }

    // get detail product
    public static List<OrderProductResponse> getDetailProduct(List<OrderProductResponse> orderProductResponses){

        for (OrderProductResponse item : orderProductResponses){
            String uri = "http://192.168.66.125:8200/api/v1.0/product/get-detail-product/" +
                    item.getProductResponse().getProductId();

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<StandardResponse<ProductResponse>> requestProduct = new HttpEntity<>(new StandardResponse<ProductResponse>());
            ResponseEntity<StandardResponse<ProductResponse>> responseProduct = restTemplate
                    .exchange(uri, HttpMethod.GET, requestProduct, new ParameterizedTypeReference<StandardResponse<ProductResponse>>() {
                    });

            StandardResponse<ProductResponse> productResponse = responseProduct.getBody();

            item.setProductResponse(productResponse.getData());
        }

        return orderProductResponses;
    }

    //get detail order
    @GetMapping("/order/{orderId}")
    ResponseEntity<Object> getDetailOrder(@PathVariable(name = "orderId") long orderId,
                                            HttpServletRequest request, HttpServletResponse response) throws Throwable{

        log.info("Mapped getDetailOrder method {{GET: /order/{orderId}}}");

        UserResponse userLogin;
        try {
            userLogin = JwtTokenProvider.getInfoUserFromToken(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new StandardResponse<>(
                            StatusResponse.UNAUTHORIZED,
                            "Bad token!!!"
                    )
            );
        }

        long userId = userLogin.getUserId();

        // get detail address
        AddressResponse addressResponse = getDetailAddress(orderService.getOrderByOrderId(userId, orderId).getAddressId(), request, response);

        // get list product
        List<OrderProductResponse> orderProductResponses = orderService.getProductByOrderId(userId, orderId)
                .stream().map(OrderProductMapper::convertToOrderProductResponse).collect(Collectors.toList());

        orderProductResponses = getDetailProduct(orderProductResponses);

        OrderResponse orderResponse = OrderMapper.convertToOrderResponse(
                orderService.getOrderByOrderId(userId, orderId), orderProductResponses
                , orderService.getPaymentByOrderId(userId, orderId), addressResponse
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "found!!",
                        orderResponse
                )
        );
    }

    // update status - cancel
    @PutMapping("/order/cancel/{orderId}")
    ResponseEntity<Object>updateStatusOrder(@PathVariable(name = "orderId") long orderId,
                                          HttpServletRequest request, HttpServletResponse response) throws Throwable{

        log.info("Mapped createOrder method {{PUT: /order/{orderId}}}");

        UserResponse userLogin;
        try {
            userLogin = JwtTokenProvider.getInfoUserFromToken(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new StandardResponse<>(
                            StatusResponse.UNAUTHORIZED,
                            "Bad token!!!"
                    )
            );
        }

        long userId = userLogin.getUserId();

        // get detail address
        AddressResponse addressResponse = getDetailAddress(orderService.getOrderByOrderId(userId, orderId).getAddressId(), request, response);

        // get list product
        List<OrderProductResponse> orderProductResponses = orderService.getProductByOrderId(userId, orderId)
                .stream().map(OrderProductMapper::convertToOrderProductResponse).collect(Collectors.toList());

        orderProductResponses = getDetailProduct(orderProductResponses);

        OrderResponse orderResponse = OrderMapper.convertToOrderResponse(
                orderService.updateOrder(orderId, userId), orderProductResponses
                , orderService.getPaymentByOrderId(userId, orderId), addressResponse
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Success!!"
                )
        );
    }

    // create order
    @PostMapping("/order/create")
    ResponseEntity<Object>createOrder(
                                      HttpServletRequest request, HttpServletResponse response) throws Throwable{

        log.info("Mapped createOrder method {{POST: /order/{orderId}}}");

        UserResponse userLogin;
        try {
            userLogin = JwtTokenProvider.getInfoUserFromToken(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new StandardResponse<>(
                            StatusResponse.UNAUTHORIZED,
                            "Bad token!!!"
                    )
            );
        }

        long userId = userLogin.getUserId();



        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Success!!"
                )
        );
    }
}
