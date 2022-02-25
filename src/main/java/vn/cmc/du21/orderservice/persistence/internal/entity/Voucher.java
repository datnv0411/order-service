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

    private String codeVoucher;
    private Timestamp startTime;
    private Timestamp endTime;
    private int timesOfUse;
    private int quantity;
    private String image;
    private String title;
    private int percentValue;
    private int upToValue;
    private int applicableValue;


    @ManyToMany(mappedBy = "vouchers")
    private List<Order> orders;

    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    private List<VoucherUser> voucherUsers;

    public Voucher() {
    }

    public Voucher(long voucherId, String codeVoucher, Timestamp startTime, Timestamp endTime, int timesOfUse, int quantity, String image, String title, int percentValue, int upToValue, int applicableValue, List<Order> orders, List<VoucherUser> voucherUsers) {
        this.voucherId = voucherId;
        this.codeVoucher = codeVoucher;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timesOfUse = timesOfUse;
        this.quantity = quantity;
        this.image = image;
        this.title = title;
        this.percentValue = percentValue;
        this.upToValue = upToValue;
        this.applicableValue = applicableValue;
        this.orders = orders;
        this.voucherUsers = voucherUsers;
    }

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }

    public String getCodeVoucher() {
        return codeVoucher;
    }

    public void setCodeVoucher(String codeVoucher) {
        this.codeVoucher = codeVoucher;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getTimesOfUse() {
        return timesOfUse;
    }

    public void setTimesOfUse(int timesOfUse) {
        this.timesOfUse = timesOfUse;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPercentValue() {
        return percentValue;
    }

    public void setPercentValue(int percentValue) {
        this.percentValue = percentValue;
    }

    public int getUpToValue() {
        return upToValue;
    }

    public void setUpToValue(int upToValue) {
        this.upToValue = upToValue;
    }

    public int getApplicableValue() {
        return applicableValue;
    }

    public void setApplicableValue(int applicableValue) {
        this.applicableValue = applicableValue;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<VoucherUser> getVoucherUsers() {
        return voucherUsers;
    }

    public void setVoucherUsers(List<VoucherUser> voucherUsers) {
        this.voucherUsers = voucherUsers;
    }
}
