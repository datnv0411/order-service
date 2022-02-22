package vn.cmc.du21.orderservice.presentation.external.request;

import vn.cmc.du21.orderservice.presentation.internal.request.ProductRequest;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;

public class OrderProductRequest {
    private long productId;
    private long price;
    private int quantity;

    public OrderProductRequest() {
    }

    public OrderProductRequest(long productId, long price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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
