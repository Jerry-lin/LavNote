package cn.edu.hust.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Settings implements Serializable{
    private String id;

    private String user_id;

    private String ui_language;

    private Timestamp created_at=new Timestamp(System.currentTimeMillis());

    private Timestamp updated_at=new Timestamp(System.currentTimeMillis());

    private Timestamp deleted_at;

    public Settings() {
    }

    public Settings(String id, String user_id, String ui_language) {
        this.id = id;
        this.user_id = user_id;
        this.ui_language = ui_language;
    }

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

    public String getUi_language() {
        return ui_language;
    }

    public void setUi_language(String ui_language) {
        this.ui_language = ui_language == null ? null : ui_language.trim();
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

    public Timestamp getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Timestamp deleted_at) {
        this.deleted_at = deleted_at;
    }
}