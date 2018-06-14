package cn.edu.hust.domain;

import java.sql.Timestamp;
import java.util.Date;

public class NoteBookUser {
    private Long id;

    private String user_id;

    private String notebook_id;

    private Byte umask;

    private Timestamp created_at=new Timestamp(System.currentTimeMillis());

    private Timestamp updated_at=new Timestamp(System.currentTimeMillis());

    public NoteBookUser() {
    }

    public NoteBookUser(String user_id, String notebook_id, Byte umask) {
        this.user_id = user_id;
        this.notebook_id = notebook_id;
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

    public String getNotebook_id() {
        return notebook_id;
    }

    public void setNotebook_id(String notebook_id) {
        this.notebook_id = notebook_id == null ? null : notebook_id.trim();
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