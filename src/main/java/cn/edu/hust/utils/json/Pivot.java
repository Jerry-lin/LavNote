package cn.edu.hust.utils.json;

import java.io.Serializable;

public class Pivot implements Serializable {
    private String note_id;
    private String tag_id;

    public void set(String note_id, String tag_id) {
        this.note_id = note_id;
        this.tag_id = tag_id;
    }

    public Pivot() {
    }

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }
}
