package vn.cmc.du21.orderservice.presentation.external.response;

import java.sql.Timestamp;

public class VoucherResponse {
    private long voucherId;
    private String codeVoucher;
    private String startTime;
    private String endTime;
    private int timesOfUse;
    private int quantity;
    private String image;
    private String title;
    private int percentValue;
    private int upToValue;
    private int applicableValue;

    public VoucherResponse() {
    }

    public VoucherResponse(long voucherId, String codeVoucher, String startTime, String emdTime
            , int timesOfUse, int quantity, String image, String title
            , int percentValue, int upToValue, int applicableValue) {
        this.voucherId = voucherId;
        this.codeVoucher = codeVoucher;
        this.startTime = startTime;
        this.endTime = emdTime;
        this.timesOfUse = timesOfUse;
        this.quantity = quantity;
        this.image = image;
        this.title = title;
        this.percentValue = percentValue;
        this.upToValue = upToValue;
        this.applicableValue = applicableValue;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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
}
