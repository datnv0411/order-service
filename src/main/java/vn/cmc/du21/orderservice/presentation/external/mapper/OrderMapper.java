package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.persistence.internal.entity.Order;
import vn.cmc.du21.orderservice.persistence.internal.entity.OrderPayment;
import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;
import vn.cmc.du21.orderservice.presentation.external.response.OrderProductResponse;
import vn.cmc.du21.orderservice.presentation.external.response.OrderResponse;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.AddressResponse;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    private OrderMapper(){super();}

    public static OrderResponse convertToOrderResponse(
            Order order , List<OrderProductResponse> productResponses
            , OrderPayment paymentResponse, AddressResponse addressResponse){

        List<VoucherResponse> voucherResponses = new ArrayList<>();
        for (Voucher item : order.getVouchers())
        {
            VoucherResponse voucher = new VoucherResponse();
            voucher.setVoucherId(item.getVoucherId());
            voucher.setCodeVoucher(item.getCodeVoucher());
            voucher.setStartTime(item.getStartTime());
            voucher.setEndTime(item.getEndTime());
            voucher.setTimesOfUse(item.getTimesOfUse());
            voucher.setQuantity(item.getQuantity());
            voucher.setImage(item.getImage());
            voucher.setTitle(item.getTitle());
            voucher.setPercentValue(item.getPercentValue());
            voucher.setUpToValue(item.getUpToValue());
            voucher.setApplicableValue(item.getApplicableValue());

            voucherResponses.add(voucher);
        }

        return new OrderResponse(order.getOrderId(), order.getUserId(), order.getAddressId(), order.getStatusOrder()
        , order.getNote(), order.getCreateTime(), order.getHoldTime(), order.getDeliveryTime()
        , voucherResponses, productResponses, paymentResponse, addressResponse);
    }
}
