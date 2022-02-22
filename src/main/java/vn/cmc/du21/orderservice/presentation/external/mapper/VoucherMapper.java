package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.persistence.internal.entity.Voucher;
import vn.cmc.du21.orderservice.presentation.external.request.VoucherRequest;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherResponse;

public class VoucherMapper {
    private VoucherMapper(){
        super();
    }

    public static Voucher convertVoucherRequestToVoucher(VoucherRequest voucherRequest)
    {
        Voucher voucher = new Voucher();
        voucher.setCodeVoucher(voucherRequest.getCodeVoucher());
        return voucher;
    }

    public static VoucherResponse convertVoucherToVoucherResponse(Voucher voucher) {
        VoucherResponse voucherResponse = new VoucherResponse();

        voucherResponse.setVoucherId(voucher.getVoucherId());
        voucherResponse.setCodeVoucher(voucher.getCodeVoucher());
        voucherResponse.setPercentValue(voucher.getPercentValue());
        voucherResponse.setUpToValue(voucher.getUpToValue());
        voucherResponse.setApplicableValue(voucher.getApplicableValue());

        return  voucherResponse;
    }
}
