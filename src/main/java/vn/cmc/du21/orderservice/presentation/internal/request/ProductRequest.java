package vn.cmc.du21.orderservice.presentation.internal.request;

import vn.cmc.du21.orderservice.presentation.internal.response.SizeResponse;

import java.util.List;

public class ProductRequest {
    private long productId;
    private String productName;
    private String quantitative;
    private String description;
    private String createTime;
    private String productImage;
    private long categoryId;
    private String categoryName;
    private List<SizeRequest> sizeRequestList;

    public ProductRequest() {
    }

    public ProductRequest(long productId, String productName, String quantitative, String description, String createTime, String productImage, long categoryId, String categoryName, List<SizeRequest> sizeRequestList) {
        this.productId = productId;
        this.productName = productName;
        this.quantitative = quantitative;
        this.description = description;
        this.createTime = createTime;
        this.productImage = productImage;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.sizeRequestList = sizeRequestList;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantitative() {
        return quantitative;
    }

    public void setQuantitative(String quantitative) {
        this.quantitative = quantitative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<SizeRequest> getSizeRequestList() {
        return sizeRequestList;
    }

    public void setSizeRequestList(List<SizeRequest> sizeRequestList) {
        this.sizeRequestList = sizeRequestList;
    }
}
