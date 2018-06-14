package cn.edu.hust.domain;

import java.sql.Timestamp;
import java.util.Date;

public class NoteBooks {
    private String id;

    private String parent_id;

    private Byte type;

    private String title;

    private Timestamp created_at=new Timestamp(System.currentTimeMillis());

    private Timestamp updated_at=new Timestamp(System.currentTimeMillis());

    private Timestamp deleted_at;


    public NoteBooks() {
    }

    public NoteBooks(String id, String parent_id, Byte type, String title) {
        this.id = id;
        this.parent_id = parent_id;
        this.type = type;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id == null ? null : parent_id.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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