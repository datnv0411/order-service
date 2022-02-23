package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.common.DateTimeUtil;
import vn.cmc.du21.orderservice.persistence.internal.entity.Order;
import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;
import vn.cmc.du21.orderservice.presentation.external.request.OrderRequest;
import vn.cmc.du21.orderservice.presentation.external.response.*;
import vn.cmc.du21.orderservice.presentation.external.response.DeliveryAddressResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    private OrderMapper(){super();}

    public static OrderResponse convertToOrderResponse(
            Order order , List<OrderProductResponse> productResponses
            , OrderPaymentResponse paymentResponse, DeliveryAddressResponse addressResponse
            , TotalOrderResponse totalResponse){

        List<VoucherResponse> voucherResponses = new ArrayList<>();
        for (Voucher item : order.getVouchers())
        {
            VoucherResponse voucher = new VoucherResponse();
            voucher.setVoucherId(item.getVoucherId());
            voucher.setCodeVoucher(item.getCodeVoucher());
            voucher.setStartTime(DateTimeUtil.timestampToString(item.getStartTime()));
            voucher.setEndTime(DateTimeUtil.timestampToString(item.getEndTime()));
            voucher.setTimesOfUse(item.getTimesOfUse());
            voucher.setQuantity(item.getQuantity());
            voucher.setImage(item.getImage());
            voucher.setTitle(item.getTitle());
            voucher.setPercentValue(item.getPercentValue());
            voucher.setUpToValue(item.getUpToValue());
            voucher.setApplicableValue(item.getApplicableValue());

            voucherResponses.add(voucher);
        }

        return new OrderResponse(order.getOrderId(), order.getUserId(), order.getStatusOrder()
                , order.getNote(), DateTimeUtil.timestampToString(order.getCreateTime())
                , DateTimeUtil.timestampToString(order.getHoldTime())
                , DateTimeUtil.timestampToString(order.getDeliveryTime()), voucherResponses, productResponses
                , paymentResponse, addressResponse, totalResponse);
    }

    public static Order convertOrderRequestToOrder(OrderRequest orderRequest) {
        Order order = new Order();

        order.setPaymentId(orderRequest.getPaymentId());
        order.setHoldTime(DateTimeUtil.stringToTimeStamp(orderRequest.getHoldTime()));
        order.setNote(orderRequest.getNote());

        order.setVouchers(orderRequest.getVoucherRequests()
                .stream()
                .map(VoucherMapper::convertVoucherRequestToVoucher)
                .collect(Collectors.toList()));

        order.setOrderProducts(orderRequest.getOrderProductRequests()
                .stream()
                .map(OrderProductMapper::convertOrderProductRequestToOrderProduct)
                .collect(Collectors.toList()));

        return order;
    }

    public static OrderResponse convertOrderToOrderResponse(Order order, List<OrderProductResponse> productResponses
            , TotalOrderResponse totalResponse){
        return new OrderResponse(order.getOrderId(), order.getUserId(), order.getStatusOrder()
                , DateTimeUtil.timestampToString(order.getCreateTime())
                , productResponses, totalResponse);
    }
}
