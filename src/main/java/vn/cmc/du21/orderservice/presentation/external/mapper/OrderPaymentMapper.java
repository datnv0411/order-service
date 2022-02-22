package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.persistence.internal.entity.OrderPayment;
import vn.cmc.du21.orderservice.presentation.external.response.OrderPaymentResponse;

public class OrderPaymentMapper {
    private OrderPaymentMapper(){ super();}

    public static OrderPaymentResponse convertToOrderPaymentResponse(OrderPayment orderPayment){
        return null;
    }
}
