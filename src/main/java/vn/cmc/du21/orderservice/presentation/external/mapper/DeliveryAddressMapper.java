package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.persistence.internal.entity.DeliveryAddress;
import vn.cmc.du21.orderservice.presentation.external.response.DeliveryAddressResponse;

public class DeliveryAddressMapper {
    private DeliveryAddressMapper() {super();}

    public static DeliveryAddressResponse convertAddressRequestToAddress (DeliveryAddress deliveryAddress){
        return new DeliveryAddressResponse(deliveryAddress.getDeliveryAddressId(), deliveryAddress.isDefault()
                , deliveryAddress.getTypeAddress(), deliveryAddress.getFullName()
                , deliveryAddress.getCellphone(), deliveryAddress.getProvince()
                , deliveryAddress.getDistrict(), deliveryAddress.getTown()
                , deliveryAddress.getSpecificAddress());
    }
}
