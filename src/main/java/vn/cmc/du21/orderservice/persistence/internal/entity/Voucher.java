package vn.cmc.du21.orderservice.persistence.internal.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long voucherId;

    private long valueVoucher;
    private Timestamp expiredTime;
    private int quantity;
    private String codeVoucher;

    @ManyToMany(mappedBy = "vouchers")
    private List<Order> orders;

    public Voucher() {
    }

    public Voucher(long voucherId, long valueVoucher, Timestamp expiredTime, int quantity, String codeVoucher, List<Order> orders) {
        this.voucherId = voucherId;
        this.valueVoucher = valueVoucher;
        this.expiredTime = expiredTime;
        this.quantity = quantity;
        this.codeVoucher = codeVoucher;
        this.orders = orders;
    }

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }

    public long getValueVoucher() {
        return valueVoucher;
    }

    public void setValueVoucher(long valueVoucher) {
        this.valueVoucher = valueVoucher;
    }

    public Timestamp getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Timestamp expiredTime) {
        this.expiredTime = expiredTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCodeVoucher() {
        return codeVoucher;
    }

    public void setCodeVoucher(String codeVoucher) {
        this.codeVoucher = codeVoucher;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
