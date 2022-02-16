package vn.cmc.du21.orderservice.common.restful;

public class StandardResponse<T> {
    private StatusResponse status;
    private String message;
    private T data;

    public StandardResponse(StatusResponse status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public StandardResponse(StatusResponse status) {
        this.status = status;
    }

    public StandardResponse(StatusResponse status, String message) {
        this.status = status;
        this.message = message;
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
}
