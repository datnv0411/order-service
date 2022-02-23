package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.persistence.internal.entity.OrderProduct;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderProductId;
import vn.cmc.du21.orderservice.presentation.external.request.OrderProductRequest;
import vn.cmc.du21.orderservice.presentation.external.response.OrderProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;

public class OrderProductMapper {
    private OrderProductMapper(){super();}

    public static OrderProductResponse convertToOrderProductResponse(OrderProduct orderProduct){
        OrderProductResponse orderProductResponse = new OrderProductResponse();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(orderProduct.getOrderProductId().getProductId());

        orderProductResponse.setProductResponse(productResponse);
        orderProductResponse.setPrice(orderProduct.getPrice());
        orderProductResponse.setQuantity(orderProduct.getQuantity());

        return orderProductResponse;
    }

    public static OrderProduct convertOrderProductRequestToOrderProduct(OrderProductRequest orderProductRequest)
    {
        OrderProduct orderProduct = new OrderProduct();

        OrderProductId orderProductId = new OrderProductId();
        orderProductId.setProductId(orderProductRequest.getProductId());
        orderProductId.setSizeId(orderProductRequest.getSizeId());

        orderProduct.setOrderProductId(orderProductId);
        orderProduct.setQuantity(orderProductRequest.getQuantity());
        orderProduct.setPrice(orderProductRequest.getPrice());
        orderProduct.setPriceSale(orderProductRequest.getPriceSale());

        return orderProduct;
    }
}
