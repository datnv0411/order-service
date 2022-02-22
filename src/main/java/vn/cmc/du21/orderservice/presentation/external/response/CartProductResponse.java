package vn.cmc.du21.orderservice.presentation.external.response;


public class CartProductResponse {
    private long productId;
    private String productName;
    private String quantitative;
    private String description;
    private String createTime;
    private String productImage;
    private long categoryId;
    private String categoryName;
    private long sizeId;
    private int quantity;
    private long price;
    private long priceSale;

    public CartProductResponse() {
    }

    public CartProductResponse(long productId, String productName, String quantitative, String description, String createTime, String productImage, long categoryId, String categoryName, long sizeId, int quantity, long price, long priceSale) {
        this.productId = productId;
        this.productName = productName;
        this.quantitative = quantitative;
        this.description = description;
        this.createTime = createTime;
        this.productImage = productImage;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.sizeId = sizeId;
        this.quantity = quantity;
        this.price = price;
        this.priceSale = priceSale;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public long getSizeId() {
        return sizeId;
    }

    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
    }
}
