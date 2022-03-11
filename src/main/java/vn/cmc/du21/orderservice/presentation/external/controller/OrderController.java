package vn.cmc.du21.orderservice.presentation.external.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vn.cmc.du21.orderservice.common.JwtTokenProvider;
import vn.cmc.du21.orderservice.common.restful.PageResponse;
import vn.cmc.du21.orderservice.common.restful.StandardResponse;
import vn.cmc.du21.orderservice.common.restful.StatusResponse;
import vn.cmc.du21.orderservice.presentation.external.mapper.*;
import vn.cmc.du21.orderservice.presentation.external.request.OrderProductRequest;
import vn.cmc.du21.orderservice.presentation.external.request.OrderRequest;
import vn.cmc.du21.orderservice.presentation.external.response.*;
import vn.cmc.du21.orderservice.presentation.internal.response.AddressResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.PaymentResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.UserResponse;
import vn.cmc.du21.orderservice.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1.0")
public class OrderController {
    @Autowired
    private Environment env;
    @Autowired
    OrderService orderService;

    private static final String GET_INFO_PAYMENT = "/api/v1.0/payment/get-info-payment";
    private static final String PATH_PAYMENT_SERVICE = "path.payment-service";

    //get detail order
    @GetMapping("/order/{orderId}")
    ResponseEntity<Object> getDetailOrder(@PathVariable(name = "orderId") long orderId,
                                            HttpServletRequest request, HttpServletResponse response) throws Throwable{

        log.info("Mapped getDetailOrder method {{GET: /order/{orderId}}}");

        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
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
        final String uri = env.getProperty(PATH_PAYMENT_SERVICE) + GET_INFO_PAYMENT
                + "?orderId=" + orderId + "&paymentId=" + orderService.getOrderByOrderId(userId, orderId).getPaymentId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<StandardResponse<PaymentResponse>> requestPayment = new HttpEntity<>(new StandardResponse<>());
        ResponseEntity<StandardResponse<PaymentResponse>> responsePayment = restTemplate
                .exchange(uri, HttpMethod.GET, requestPayment, new ParameterizedTypeReference<StandardResponse<PaymentResponse>>() {
                });

        PaymentResponse orderPaymentResponse =responsePayment.getBody().getData();

        // get total order
        TotalOrderResponse totalResponse = getTotalOrder(userId, orderId);

        OrderResponse orderResponse = OrderMapper.convertToOrderResponse(
                orderService.getOrderByOrderId(userId, orderId)
                , orderProductResponses
                , orderPaymentResponse
                , addressResponse
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

        log.info("Mapped updateStatusOrder method {{PUT: /order/cancel/{orderId}}}");

        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
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
        PaymentResponse orderPaymentResponse = new PaymentResponse();

        // get total order
        TotalOrderResponse totalResponse = getTotalOrder(userId, orderId);

        OrderResponse orderResponse = OrderMapper.convertToOrderResponse(
                orderService.updateOrder(orderId, userId), orderProductResponses
                , orderPaymentResponse, addressResponse
                , totalResponse
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Success!!",
                        orderResponse
                )
        );
    }

    // update status after payment
    @PutMapping("/order/paid")
    ResponseEntity<Object> updateStatusOrderAfterPaid(@RequestParam(name = "orderId") long orderId,
                                             @RequestParam(name = "paymentId") long paymentId,
                                             HttpServletRequest request, HttpServletResponse response) throws Throwable{

        log.info("Mapped updateStatusOrderAfterPaid method {{PUT: /order/paid/{orderId}}}");

        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        long userId = userLogin.getUserId();

        // get payment response
        final String uri = env.getProperty(PATH_PAYMENT_SERVICE) + GET_INFO_PAYMENT
                + "?orderId=" + orderId + "&paymentId=" + paymentId;

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<StandardResponse<PaymentResponse>> requestPayment = new HttpEntity<>(new StandardResponse<>());
        ResponseEntity<StandardResponse<PaymentResponse>> responsePayment = restTemplate
                .exchange(uri, HttpMethod.GET, requestPayment, new ParameterizedTypeReference<StandardResponse<PaymentResponse>>() {
                });

        PaymentResponse paymentResponse = responsePayment.getBody().getData();

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
        PaymentResponse orderPaymentResponse = new PaymentResponse();

        // get total order
        TotalOrderResponse totalResponse = getTotalOrder(userId, orderId);

        OrderResponse orderResponse = OrderMapper.convertToOrderResponse(
                orderService.updateOrderAfterPaid(orderId, userId, paymentResponse.getStatus()), orderProductResponses
                , orderPaymentResponse, addressResponse
                , totalResponse
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Success!!",
                        orderResponse
                )
        );
    }

    // create order
    @PostMapping("/order/create")
    ResponseEntity<Object>createOrder(@RequestBody OrderRequest orderRequest,
                                      HttpServletRequest request, HttpServletResponse response) throws Throwable{

        log.info("Mapped createOrder method {{POST: /order/create");

        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        long userId = userLogin.getUserId();
        orderRequest.setUserId(userId);

        // get detail address by addressId - user-service,
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(request.getHeader("Authorization").split(" ")[1]);
        String uri = env.getProperty("path.user-service")+"/api/v1.0/address/" + orderRequest.getAddressId() + "";

        HttpEntity<StandardResponse<AddressResponse>> entity = new HttpEntity<>(new StandardResponse<>(), httpHeaders);

        ResponseEntity<StandardResponse<AddressResponse>> res = restTemplate
                .exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<StandardResponse<AddressResponse>>() {});

        AddressResponse addressResponse = res.getBody().getData();

        // get price + price sale product

        for (OrderProductRequest item : orderRequest.getOrderProductRequests()) {
             uri = env.getProperty("path.inventory-service") + "/api/v1.0/product/get-detail-product/" +
                    item.getProductId();

            restTemplate = new RestTemplate();
            HttpEntity<StandardResponse<ProductResponse>> requestItem = new HttpEntity<>(new StandardResponse<>());
            ResponseEntity<StandardResponse<ProductResponse>> responseItem = restTemplate
                    .exchange(uri, HttpMethod.GET, requestItem, new ParameterizedTypeReference<StandardResponse<ProductResponse>>() {
                    });

                ProductResponse productResponse = responseItem.getBody().getData();

                item.setPrice(item.getPriceByProductIdAndSizeId(productResponse));
                item.setPriceSale(item.getPriceSaleByProductIdAndSizeId(productResponse));
        }

        //create order + delivery address
        long orderId = orderService.createOrder(
                OrderMapper.convertOrderRequestToOrder(orderRequest),
                DeliveryAddressMapper.convertAddressResponseToDeliveryAddress(addressResponse)
        ).getOrderId();

        // get name payment
        uri = env.getProperty(PATH_PAYMENT_SERVICE) + GET_INFO_PAYMENT
                + "?orderId=" + orderId + "&paymentId=" + orderRequest.getPaymentId();

        restTemplate = new RestTemplate();
        HttpEntity<StandardResponse<PaymentResponse>> requestPayment = new HttpEntity<>(new StandardResponse<>());
        ResponseEntity<StandardResponse<PaymentResponse>> responsePayment = restTemplate
                .exchange(uri, HttpMethod.GET, requestPayment, new ParameterizedTypeReference<StandardResponse<PaymentResponse>>() {
                });

        PaymentResponse paymentResponse = responsePayment.getBody().getData();
        String paymentName = paymentResponse.getPaymentName();
        uri = env.getProperty(PATH_PAYMENT_SERVICE) + "/api/v1.0/payment/" + paymentName.toLowerCase() + "/" + orderId;

        if(paymentName.equals("Vnpay"))
        {
            restTemplate = new RestTemplate();
            HttpEntity<StandardResponse<String>> requestLink = new HttpEntity<>(new StandardResponse<>(), httpHeaders);
            ResponseEntity<StandardResponse<String>> responseLink = restTemplate
                .exchange(uri, HttpMethod.GET, requestLink, new ParameterizedTypeReference<StandardResponse<String>>() {});
            String linkPay = responseLink.getBody().getData();
            response.sendRedirect(linkPay);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new StandardResponse<>(
                            StatusResponse.SUCCESSFUL,
                            "Success!!"
                    )
            );
        }
        else if(paymentName.equals("Momo") || paymentName.equals("Zalopay"))
        {
            restTemplate = new RestTemplate();
            HttpEntity<byte[]> requestQR = new HttpEntity<>(httpHeaders);
            ResponseEntity<byte[]> responseQR = restTemplate
                    .exchange(uri, HttpMethod.GET, requestQR, new ParameterizedTypeReference<byte[]>() {
                    });

            byte[] bytes = responseQR.getBody();
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new StandardResponse<>(
                        StatusResponse.SUCCESSFUL,
                        "Success!!"
                )
        );
    }

    // get list order
    @GetMapping("/order")
    ResponseEntity<Object> getListOrder(@RequestParam(value = "statusOrder",required = false) String statusOrder
                                        , @RequestParam(value = "startTime", required = false) String startTime
                                        , @RequestParam(value = "endTime", required = false) String endTime
                                        , @RequestParam(value = "page",required = false) String page
                                        , @RequestParam(value = "size",required = false) String size
                                        ,  HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("Mapped getListOrder method {{GET: /order}}");

        UserResponse userLogin = JwtTokenProvider.getInfoUserFromToken(request, env);
        long userId = userLogin.getUserId();

        if (page==null || !page.chars().allMatch(Character::isDigit) || page.equals("")) page="1";
        if (size==null || !size.chars().allMatch(Character::isDigit) || size.equals("")) size="5";
        if (statusOrder==null || statusOrder.equals("")) statusOrder="all";
        if (endTime==null || endTime.equals("")) endTime=LocalDateTime.now().toString();
        if (startTime==null || startTime.equals("")) startTime=(LocalDateTime.now().minusMonths(1)).toString();

        int pageInt = Integer.parseInt(page)-1;
        int sizeInt = Integer.parseInt(size);

        Page<OrderResponse> listOrder = orderService.getListOrder(pageInt, sizeInt, statusOrder, startTime, endTime, userId)
                .map(u -> {
                    // get list product
                    List<OrderProductResponse> orderProductResponses = orderService.getProductByOrderId(userId, u.getOrderId())
                            .stream().map(OrderProductMapper::convertToOrderProductResponse).collect(Collectors.toList());

                    orderProductResponses = getDetailProduct(orderProductResponses);

                    // get total order
                    TotalOrderResponse totalResponse = getTotalOrder(userId, u.getOrderId());

                    return OrderMapper.convertOrderToOrderResponse(u, orderProductResponses, totalResponse);
                });

        return ResponseEntity.status(HttpStatus.OK).body(
                new PageResponse<Object>(
                        StatusResponse.SUCCESSFUL
                        , "Successfully"
                        , listOrder.getContent()
                        , pageInt +1
                        , listOrder.getTotalPages()
                        , listOrder.getTotalElements()
                )
        );
    }

    // get detail address
    public DeliveryAddressResponse getDetailAddress(long addressId,
                                                    HttpServletRequest request){

        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(request.getHeader("Authorization").split(" ")[1]);
        String uri = env.getProperty("path.user-service")+"/api/v1.0/address/" + addressId + "";

        HttpEntity<StandardResponse<DeliveryAddressResponse>> entity = new HttpEntity<>(new StandardResponse<>(), httpHeaders);
        ResponseEntity<StandardResponse<DeliveryAddressResponse>> res = restTemplate
                .exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<StandardResponse<DeliveryAddressResponse>>() {});

        return res.getBody().getData();
    }

    // get detail product
    public List<OrderProductResponse> getDetailProduct(List<OrderProductResponse> orderProductResponses){

        for (OrderProductResponse item : orderProductResponses){
                String uri = env.getProperty("path.inventory-service") + "/api/v1.0/product/get-detail-product/" +
                        item.getProductResponse().getProductId();

                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<StandardResponse<ProductResponse>> requestProduct = new HttpEntity<>(new StandardResponse<>());
                ResponseEntity<StandardResponse<ProductResponse>> responseProduct = restTemplate
                        .exchange(uri, HttpMethod.GET, requestProduct, new ParameterizedTypeReference<StandardResponse<ProductResponse>>() {
                        });

                StandardResponse<ProductResponse> productResponse = responseProduct.getBody();

                item.setProductResponse(productResponse.getData());
        }

        return orderProductResponses;
    }

    // get total order
    public TotalOrderResponse getTotalOrder(long userId, long orderId){
        TotalOrderResponse totalResponse = new TotalOrderResponse();

        totalResponse.setTotalPrice(orderService.totalPrice(orderId));
        totalResponse.setTotalDiscount(orderService.totalDiscount(orderId));
        totalResponse.setShippingFee(orderService.shippingFee(orderId));
        totalResponse.setTotalBeforeVAT(orderService.totalBeforeVAT(orderId));
        totalResponse.setTotalVAT(orderService.totalVAT(orderId));
        totalResponse.setTotalAfterVAT(orderService.totalAfterVAT(orderId));
        totalResponse.setTotalVoucherDiscount(orderService.totalVoucherDiscount(userId, orderId));
        totalResponse.setTotalOrder(orderService.totalOrder(userId, orderId));

        return totalResponse;
    }
}
