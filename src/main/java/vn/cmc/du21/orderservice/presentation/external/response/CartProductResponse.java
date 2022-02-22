package vn.cmc.du21.orderservice.presentation.external.response;

import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.SizeResponse;

public class CartProductResponse {
    private ProductResponse productResponse;
    private long sizeId;
    private int quantity;

    private long totalPrice;

    public long getPriceWithoutSale()
    {
        for (SizeResponse item : productResponse.getSizeResponseList())
        {
            if(item.getSizeId() == sizeId)
            {
                return item.getPrice() * quantity;
            }
        }

        throw new RuntimeException("Size is not available -- get total price in CartProductResponse !!!");
    }

    public CartProductResponse() {
    }

    public CartProductResponse(ProductResponse productResponse, long sizeId, int quantity) {
        this.productResponse = productResponse;
        this.sizeId = sizeId;
        this.quantity = quantity;
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }

    public void setProductResponse(ProductResponse productResponse) {
        this.productResponse = productResponse;
    }

    public long getSizeId() {
        return sizeId;
    }

    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTotalPrice() {
        for (SizeResponse item : productResponse.getSizeResponseList())
        {
            if(item.getSizeId() == sizeId)
            {
                return item.getPriceSale() * quantity;
            }
        }

        throw new RuntimeException("Size is not available -- get total price in CartProductResponse !!!");
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
