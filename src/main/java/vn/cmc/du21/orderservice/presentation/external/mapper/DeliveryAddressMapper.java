package vn.cmc.du21.orderservice.presentation.external.mapper;

import vn.cmc.du21.orderservice.persistence.internal.entity.DeliveryAddress;
import vn.cmc.du21.orderservice.presentation.external.response.DeliveryAddressResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.AddressResponse;

public class DeliveryAddressMapper {
    private DeliveryAddressMapper(){super();}

    public static DeliveryAddress convertAddressResponseToDeliveryAddress(AddressResponse addressResponse)
    {
        DeliveryAddress deliveryAddress = new DeliveryAddress();

        deliveryAddress.setTypeAddress(addressResponse.getTypeAddress());
        deliveryAddress.setFullName(addressResponse.getFullName());
        deliveryAddress.setCellphone(addressResponse.getCellphone());
        deliveryAddress.setProvince(deliveryAddress.getProvince());
        deliveryAddress.setDistrict(deliveryAddress.getDistrict());
        deliveryAddress.setTown(addressResponse.getTown());
        deliveryAddress.setSpecificAddress(addressResponse.getSpecificAddress());

        return deliveryAddress;
    }

    public static DeliveryAddressResponse convertDeliveryAddressToDeliveryAddressResponse(DeliveryAddress deliveryAddress) {
        DeliveryAddressResponse deliveryAddressResponse = new DeliveryAddressResponse();

        deliveryAddressResponse.setAddressId(deliveryAddress.getDeliveryAddressId());
        deliveryAddressResponse.setTypeAddress(deliveryAddress.getTypeAddress());
        deliveryAddressResponse.setFullName(deliveryAddress.getFullName());
        deliveryAddressResponse.setCellphone(deliveryAddress.getCellphone());
        deliveryAddressResponse.setProvince(deliveryAddress.getProvince());
        deliveryAddressResponse.setDistrict(deliveryAddress.getDistrict());
        deliveryAddressResponse.setTown(deliveryAddress.getTown());
        deliveryAddressResponse.setSpecificAddress(deliveryAddress.getSpecificAddress());

        return deliveryAddressResponse;
    }
}
