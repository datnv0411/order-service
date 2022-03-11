package vn.cmc.du21.orderservice.presentation.external.request;

public class MenuRequest {
    private long menuId;
    private long sizeId;

    public MenuRequest() {
    }

    public MenuRequest(long menuId, long sizeId) {
        this.menuId = menuId;
        this.sizeId = sizeId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public long getSizeId() {
        return sizeId;
    }

    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
    }
}
