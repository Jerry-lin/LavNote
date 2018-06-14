package cn.edu.hust.domain;

import java.sql.Timestamp;
import java.util.Date;

public class Languages {
    private String id;

    private String language_code;

    private Timestamp deleted_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code == null ? null : language_code.trim();
    }

    public Timestamp getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Timestamp deleted_at) {
        this.deleted_at = deleted_at;
    }
}