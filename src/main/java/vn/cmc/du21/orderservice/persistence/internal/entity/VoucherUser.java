package vn.cmc.du21.orderservice.persistence.internal.entity;

import javax.persistence.*;

@Entity
@Table(name = "voucheruser")
public class VoucherUser {
    @EmbeddedId
    private VoucherUserId voucherUserId;

    private int usedTimes;

    @ManyToOne
    @MapsId("voucherId")
    private Voucher voucher;

    public VoucherUser() {
    }

    public VoucherUser(VoucherUserId voucherUserId, int usedTimes, Voucher voucher) {
        this.voucherUserId = voucherUserId;
        this.usedTimes = usedTimes;
        this.voucher = voucher;
    }

    public VoucherUserId getVoucherUserId() {
        return voucherUserId;
    }

    public void setVoucherUserId(VoucherUserId voucherUserId) {
        this.voucherUserId = voucherUserId;
    }

    public int getUsedTimes() {
        return usedTimes;
    }

    public void setUsedTimes(int usedTimes) {
        this.usedTimes = usedTimes;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
}
