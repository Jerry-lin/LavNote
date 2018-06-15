package cn.edu.hust.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Shortcuts implements Serializable{
    private String id;

    private String user_id;

    private String notebook_id;

    private Byte sortkey;

    private Timestamp created_at=new Timestamp(System.currentTimeMillis());

    private Timestamp updated_at=new Timestamp(System.currentTimeMillis());

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public String getNotebook_id() {
        return notebook_id;
    }

    public void setNotebook_id(String notebook_id) {
        this.notebook_id = notebook_id == null ? null : notebook_id.trim();
    }

    public Byte getSortkey() {
        return sortkey;
    }

    public void setSortkey(Byte sortkey) {
        this.sortkey = sortkey;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}