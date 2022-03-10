package vn.cmc.du21.orderservice.persistence.internal.entity;

import javax.persistence.*;

@Entity
@Table(name = "deliveryaddress")
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long deliveryAddressId;
    private String typeAddress;
    private String fullName;
    private String cellphone;
    private String province;
    private String district;
    private String town;
    private String specificAddress;

    public DeliveryAddress() {
    }

    public DeliveryAddress(long addressId, String typeAddress, String fullName, String cellphone, String province, String district, String town, String specificAddress) {
        this.deliveryAddressId = addressId;
        this.typeAddress = typeAddress;
        this.fullName = fullName;
        this.cellphone = cellphone;
        this.province = province;
        this.district = district;
        this.town = town;
        this.specificAddress = specificAddress;
    }

    public long getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(long deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public String getTypeAddress() {
        return typeAddress;
    }

    public void setTypeAddress(String typeAddress) {
        this.typeAddress = typeAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }
}
