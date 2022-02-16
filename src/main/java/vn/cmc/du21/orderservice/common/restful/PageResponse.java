package vn.cmc.du21.orderservice.common.restful;

public class PageResponse<T> {
    private StatusResponse status;
    private String message;
    private T data;
    private int page;
    private int totalPage;
    private Long totalRecord;

    public PageResponse(StatusResponse status, String message, T data, int page, int totalPage, Long totalRecord) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.page = page;
        this.totalPage = totalPage;
        this.totalRecord = totalRecord;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }
}