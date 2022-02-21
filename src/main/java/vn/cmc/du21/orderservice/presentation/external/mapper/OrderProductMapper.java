package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.persistence.internal.entity.Order;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderProduct;
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
}
