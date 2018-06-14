package cn.edu.hust.domain;

public class Sessions {
    private String id;

    private Integer last_activity;

    private String payload;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getLast_activity() {
        return last_activity;
    }

    public void setLast_activity(Integer last_activity) {
        this.last_activity = last_activity;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload == null ? null : payload.trim();
    }
}