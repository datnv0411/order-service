package vn.cmc.du21.orderservice.presentation.external.mapper;


import vn.cmc.du21.orderservice.persistence.internal.entity.VoucherUser;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherResponse;
import vn.cmc.du21.orderservice.presentation.external.response.VoucherUserResponse;

public class VoucherUserMapper {
    private VoucherUserMapper() {
        super();
    }

    public static VoucherUserResponse convertVoucherUserTpVoucherResponse(VoucherUser voucherUser){
        VoucherResponse voucherResponse = VoucherMapper.convertVouchertoVoucherResponseFull(voucherUser.getVoucher());
    return new VoucherUserResponse(voucherUser.getUsedTimes(), voucherResponse);
    }
}
