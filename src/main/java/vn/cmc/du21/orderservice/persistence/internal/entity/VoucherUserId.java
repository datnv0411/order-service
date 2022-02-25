package vn.cmc.du21.orderservice.persistence.internal.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VoucherUserId implements Serializable {
    @Column(name = "voucherId")
    private long voucherId;

    @Column(name = "userId")
    private long userId;

    public VoucherUserId() {
    }

    public VoucherUserId(long voucherId, long userId) {
        this.voucherId = voucherId;
        this.userId = userId;
    }

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
