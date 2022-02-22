package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.common.DateTimeUtil;
import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherResponse;

public class VoucherMapper {
public static VoucherResponse convertVouchertoVoucherResponse(Voucher voucher){
    String startTime = voucher.getStartTime() == null ? null : DateTimeUtil.timestampToString(voucher.getStartTime());
    String endTime = voucher.getEndTime() == null ? null : DateTimeUtil.timestampToString(voucher.getEndTime());
    return new VoucherResponse(voucher.getVoucherId(),voucher.getCodeVoucher(),startTime,endTime,
            voucher.getTimesOfUse(),voucher.getQuantity(),voucher.getImage(),voucher.getTitle(),
            voucher.getPercentValue(),voucher.getUpToValue(),voucher.getApplicableValue());
}
}
