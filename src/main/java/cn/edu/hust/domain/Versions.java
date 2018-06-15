package cn.edu.hust.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Versions implements Serializable{
    private String id;

    private String previous_id;

    private String next_id;

    private String title;

    private String content_preview;

    private Timestamp created_at=new Timestamp(System.currentTimeMillis());

    private Timestamp updated_at=new Timestamp(System.currentTimeMillis());

    private Timestamp deleted_at;

    private String user_id;

    private String content;

    private Users user;

    public Versions() {
    }

    public Versions(String id, String previous_id, String next_id, String title, String content_preview, String user_id, String content) {
        this.id = id;
        this.previous_id = previous_id;
        this.next_id = next_id;
        this.title = title;
        this.content_preview = content_preview;
        this.user_id = user_id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPrevious_id() {
        return previous_id;
    }

    public void setPrevious_id(String previous_id) {
        this.previous_id = previous_id == null ? null : previous_id.trim();
    }

    public String getNext_id() {
        return next_id;
    }

    public void setNext_id(String next_id) {
        this.next_id = next_id == null ? null : next_id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent_preview() {
        return content_preview;
    }

    public void setContent_preview(String content_preview) {
        this.content_preview = content_preview == null ? null : content_preview.trim();
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}