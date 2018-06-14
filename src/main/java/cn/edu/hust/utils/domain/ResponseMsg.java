package cn.edu.hust.utils.domain;

public class ResponseMsg<T> {

    private int status;

    private T response;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
