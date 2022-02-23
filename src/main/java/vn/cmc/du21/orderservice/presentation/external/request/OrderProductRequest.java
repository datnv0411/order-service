package vn.cmc.du21.orderservice.presentation.external.request;

import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;
import vn.cmc.du21.orderservice.presentation.internal.response.SizeResponse;

public class OrderProductRequest {
    private long productId;
    private long sizeId;
    private int quantity;
    private long price;
    private long priceSale;

    public OrderProductRequest() {
    }

    public OrderProductRequest(long productId, long sizeId, int quantity, long price, long priceSale) {
        this.productId = productId;
        this.sizeId = sizeId;
        this.quantity = quantity;
        this.price = price;
        this.priceSale = priceSale;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(long priceSale) {
        this.priceSale = priceSale;
    }

    public long getPriceByProductIdAndSizeId(ProductResponse productResponse)
    {
        for (SizeResponse item : productResponse.getSizeResponseList())
        {
            if(item.getSizeId() == sizeId)
            {
                return item.getPrice();
            }
        }

        throw new RuntimeException("Size is not available -- get price in OrderProductRequest !!!");
    }

    public long getPriceSaleByProductIdAndSizeId(ProductResponse productResponse)
    {
        for (SizeResponse item : productResponse.getSizeResponseList())
        {
            if(item.getSizeId() == sizeId)
            {
                return item.getPriceSale();
            }
        }

        throw new RuntimeException("Size is not available -- get price sale in OrderProductRequest !!!");
    }
}
