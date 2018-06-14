package cn.edu.hust.domain;

import java.sql.Timestamp;
import java.util.Date;

public class NoteUser {
    private Long id;

    private String user_id;

    private String note_id;

    private Byte umask;

    private Timestamp created_at=new Timestamp(System.currentTimeMillis());

    private Timestamp updated_at=new Timestamp(System.currentTimeMillis());

    public NoteUser() {
    }

    public NoteUser(String user_id, String note_id, Byte umask) {
        this.user_id = user_id;
        this.note_id = note_id;
        this.umask = umask;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id == null ? null : note_id.trim();
    }

    public Byte getUmask() {
        return umask;
    }

    public void setUmask(Byte umask) {
        this.umask = umask;
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