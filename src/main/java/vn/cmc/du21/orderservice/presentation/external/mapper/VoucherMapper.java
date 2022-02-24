package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.common.DateTimeUtil;
import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;
import vn.cmc.du21.orderservice.presentation.external.request.VoucherRequest;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherResponse;

public class VoucherMapper {
    private VoucherMapper() {
        super();
    }

    public static Voucher convertVoucherRequestToVoucher(VoucherRequest voucherRequest) {
        Voucher voucher = new Voucher();
        voucher.setCodeVoucher(voucherRequest.getCodeVoucher());
        return voucher;
    }

//    public static VoucherResponse convertVoucherToVoucherResponse(Voucher voucher) {
//        VoucherResponse voucherResponse = new VoucherResponse();
//
//        voucherResponse.setVoucherId(voucher.getVoucherId());
//        voucherResponse.setCodeVoucher(voucher.getCodeVoucher());
//        voucherResponse.setPercentValue(voucher.getPercentValue());
//        voucherResponse.setUpToValue(voucher.getUpToValue());
//        voucherResponse.setApplicableValue(voucher.getApplicableValue());
//
//        return voucherResponse;
//    }

    public static VoucherResponse convertVoucherToVoucherResponse(Voucher voucher) {
        String startTime = voucher.getStartTime() == null ? null : DateTimeUtil.timestampToString(voucher.getStartTime());
        String endTime = voucher.getEndTime() == null ? null : DateTimeUtil.timestampToString(voucher.getEndTime());
        return new VoucherResponse(voucher.getVoucherId(), voucher.getCodeVoucher(), startTime, endTime,
                voucher.getTimesOfUse(), voucher.getQuantity(), voucher.getImage(), voucher.getTitle(),
                voucher.getPercentValue(), voucher.getUpToValue(), voucher.getApplicableValue());
    }
}


