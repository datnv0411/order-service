package vn.cmc.du21.orderservice.presentation.external.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.orderservice.common.JwtTokenProvider;
import vn.cmc.du21.orderservice.common.restful.StandardResponse;
import vn.cmc.du21.orderservice.common.restful.StatusResponse;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderProduct;
import vn.cmc.du21.orderservice.presentation.external.mapper.*;
import vn.cmc.du21.orderservice.presentation.external.request.OrderProductRequest;
import vn.cmc.du21.orderservice.presentation.external.request.OrderRequest;
import vn.cmc.du21.orderservice.presentation.external.response.*;
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
    private Environment env;
    @Autowired
    OrderService orderService;

    // get detail address
    public DeliveryAddressResponse getDetailAddress(long addressId,
                                                    HttpServletRequest request, HttpServletResponse response){

        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(request.getHeader("Authorization").split(" ")[1]);
        String uri = env.getProperty("path.user-service")+"/api/v1.0/address/" + addressId + "";

        HttpEntity<StandardResponse<DeliveryAddressResponse>> entity = new HttpEntity<>(new StandardResponse<DeliveryAddressResponse>(), httpHeaders);
        ResponseEntity<StandardResponse<DeliveryAddressResponse>> res = restTemplate
                .exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<StandardResponse<DeliveryAddressResponse>>() {});

        DeliveryAddressResponse addressResponse = res.getBody().getData();

        return addressResponse;
    }

    // get detail product
    public List<OrderProductResponse> getDetailProduct(List<OrderProductResponse> orderProductResponses){

        for (OrderProductResponse item : orderProductResponses){
            String uri = env.getProperty("path.inventory-service") + "/api/v1.0/product/get-detail-product/" +
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
        DeliveryAddressResponse addressResponse = DeliveryAddressMapper.convertDeliveryAddressToDeliveryAddressResponse(
                orderService.getDeliveryAddressByOrderId(
                    orderService.getOrderByOrderId(userId, orderId).getDeliveryAddress().getDeliveryAddressId()
        ));

        // get list product
        List<OrderProductResponse> orderProductResponses = orderService.getProductByOrderId(userId, orderId)
                .stream().map(OrderProductMapper::convertToOrderProductResponse).collect(Collectors.toList());

        orderProductResponses = getDetailProduct(orderProductResponses);

        // get order payment
        OrderPaymentResponse orderPaymentResponse = OrderPaymentMapper.convertToOrderPaymentResponse(orderService.getPaymentByOrderId(userId, orderId));

        // get total order
        TotalOrderResponse totalResponse = new TotalOrderResponse();

        totalResponse.setTotalPrice(orderService.totalPrice(orderId));
        totalResponse.setTotalDiscount(orderService.totalDiscount(orderId));
        totalResponse.setShippingFee(orderService.shippingFee(orderId));
        totalResponse.setTotalBeforeVAT(orderService.totalBeforeVAT(orderId));
        totalResponse.setTotalVAT(orderService.totalVAT(orderId));
        totalResponse.setTotalAfterVAT(orderService.totalAfterVAT(orderId));
        totalResponse.setTotalVoucherDiscount(orderService.totalVoucherDiscount(userId, orderId));
        totalResponse.setTotalOrder(orderService.totalOrder(userId, orderId));

        OrderResponse orderResponse = OrderMapper.convertToOrderResponse(
                orderService.getOrderByOrderId(userId, orderId), orderProductResponses
                , orderPaymentResponse, addressResponse
                , totalResponse
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
    ResponseEntity<Object> updateStatusOrder(@PathVariable(name = "orderId") long orderId,
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
        DeliveryAddressResponse addressResponse = DeliveryAddressMapper.convertDeliveryAddressToDeliveryAddressResponse(
                orderService.getDeliveryAddressByOrderId(
                        orderService.getOrderByOrderId(userId, orderId).getDeliveryAddress().getDeliveryAddressId()
                ));

        // get list product
        List<OrderProductResponse> orderProductResponses = orderService.getProductByOrderId(userId, orderId)
                .stream().map(OrderProductMapper::convertToOrderProductResponse).collect(Collectors.toList());

        orderProductResponses = getDetailProduct(orderProductResponses);

        // get order payment
        OrderPaymentResponse orderPaymentResponse = OrderPaymentMapper.convertToOrderPaymentResponse(orderService.getPaymentByOrderId(userId, orderId));

        // get total order
        TotalOrderResponse totalResponse = new TotalOrderResponse();

        totalResponse.setTotalPrice(orderService.totalPrice(orderId));
        totalResponse.setTotalDiscount(orderService.totalDiscount(orderId));
        totalResponse.setShippingFee(orderService.shippingFee(orderId));
        totalResponse.setTotalBeforeVAT(orderService.totalBeforeVAT(orderId));
        totalResponse.setTotalVAT(orderService.totalVAT(orderId));
        totalResponse.setTotalAfterVAT(orderService.totalAfterVAT(orderId));
        totalResponse.setTotalVoucherDiscount(orderService.totalVoucherDiscount(userId, orderId));
        totalResponse.setTotalOrder(orderService.totalOrder(userId, orderId));


        OrderResponse orderResponse = OrderMapper.convertToOrderResponse(
                orderService.updateOrder(orderId, userId), orderProductResponses
                , orderPaymentResponse, addressResponse
                , totalResponse
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
    ResponseEntity<Object>createOrder(@RequestBody OrderRequest orderRequest,
                                      HttpServletRequest request, HttpServletResponse response) throws Throwable{

        log.info("Mapped createOrder method {{POST: /order/{orderId}}}");

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

        long userId = 3;
        orderRequest.setUserId(userId);

        // get detail address by addressId - user-service,
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(request.getHeader("Authorization").split(" ")[1]);
        String uri = env.getProperty("path.user-service")+"/api/v1.0/address/" + orderRequest.getAddressId() + "";

        HttpEntity<StandardResponse<AddressResponse>> entity = new HttpEntity<>(new StandardResponse<AddressResponse>(), httpHeaders);
        ResponseEntity<StandardResponse<AddressResponse>> res = restTemplate
                .exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<StandardResponse<AddressResponse>>() {});

        AddressResponse addressResponse = res.getBody().getData();

        // get price + price sale product

        for (OrderProductRequest item : orderRequest.getOrderProductRequests()) {
             uri = env.getProperty("path.inventory-service") + "/api/v1.0/product/get-detail-product/" +
                    item.getProductId();

            restTemplate = new RestTemplate();
            HttpEntity<StandardResponse<ProductResponse>> requestItem = new HttpEntity<>(new StandardResponse<ProductResponse>());
            ResponseEntity<StandardResponse<ProductResponse>> responseItem = restTemplate
                    .exchange(uri, HttpMethod.GET, requestItem, new ParameterizedTypeReference<StandardResponse<ProductResponse>>() {
                    });

            ProductResponse productResponse = responseItem.getBody().getData();

            item.setPrice(item.getPriceByProductIdAndSizeId(productResponse));
            item.setPriceSale(item.getPriceSaleByProductIdAndSizeId(productResponse));
        }

        //create order + delivery address
        orderService.createOrder(
                OrderMapper.convertOrderRequestToOrder(orderRequest),
                DeliveryAddressMapper.convertAddressResponseToDeliveryAddress(addressResponse)
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Success!!"
                )
        );
    }
}
