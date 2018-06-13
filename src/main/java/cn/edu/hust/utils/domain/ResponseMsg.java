package cn.edu.hust.utils.domain;

public class ResponseMsg<T> {

    private boolean success;

    private T response;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
