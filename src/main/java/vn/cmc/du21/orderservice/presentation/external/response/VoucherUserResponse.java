package vn.cmc.du21.orderservice.presentation.external.response;

import vn.cmc.du21.orderservice.common.DateTimeUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class VoucherUserResponse {
    private int usedTimes;
    private VoucherResponse voucher;

    public VoucherUserResponse() {
    }

    public VoucherUserResponse(int usedTimes, VoucherResponse voucherResponse) {
        this.usedTimes = usedTimes;
        this.voucher = voucherResponse;
    }

    public String getStatusVoucher()
    {
        if(Timestamp.valueOf(voucher.getEndTime()).before(DateTimeUtil.getTimeNow()))
        {
            return "Hết hạn";
        }

        if(voucher.getQuantity() <= 0)
        {
            return "Hết lượt";
        }

        if(usedTimes >= voucher.getTimesOfUse())
        {
            return "Đã sử dụng";
        }

        return "";
    }

    public int getUsedTimes() {
        return usedTimes;
    }

    public void setUsedTimes(int usedTimes) {
        this.usedTimes = usedTimes;
    }

    public VoucherResponse getVoucherResponse() {
        return voucher;
    }

    public void setVoucherResponse(VoucherResponse voucherResponse) {
        this.voucher = voucherResponse;
    }
}
