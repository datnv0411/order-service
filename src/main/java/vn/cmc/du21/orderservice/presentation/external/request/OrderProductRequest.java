package vn.cmc.du21.orderservice.presentation.external.request;

import vn.cmc.du21.orderservice.presentation.internal.request.ProductRequest;
import vn.cmc.du21.orderservice.presentation.internal.response.ProductResponse;

public class OrderProductRequest {
    private ProductRequest productRequest;
    private long price;
    private int quantity;

    public OrderProductRequest() {
    }

    public OrderProductRequest(ProductRequest productRequest, long price, int quantity) {
        this.productRequest = productRequest;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductRequest getProductRequest() {
        return productRequest;
    }

    public void setProductRequest(ProductRequest productRequest) {
        this.productRequest = productRequest;
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
