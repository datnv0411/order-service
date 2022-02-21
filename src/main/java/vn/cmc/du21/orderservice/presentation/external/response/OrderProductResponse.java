package vn.cmc.du21.orderservice.presentation.external.response;

import vn.cmc.du21.orderservice.persistence.internal.entity.OrderProductId;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;

public class OrderProductResponse {
    private ProductResponse productResponse;
    private long price;
    private int quantity;

    public OrderProductResponse() {
    }

    public OrderProductResponse(ProductResponse productResponse, long price, int quantity) {
        this.productResponse = productResponse;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }

    public void setProductResponse(ProductResponse productResponse) {
        this.productResponse = productResponse;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
