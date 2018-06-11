package cn.edu.hust.domain;

import java.util.LinkedList;
import java.util.List;

public class Note {
    private String created_at;
    private String id;
    private String notebook_id;
    private List<Tag> tags=new LinkedList<Tag>();
    private String updated_at;
    private List<User> users=new LinkedList<User>();
    private String version_id;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotebook_id() {
        return notebook_id;
    }

    public void setNotebook_id(String notebook_id) {
        this.notebook_id = notebook_id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getVersion_id() {
        return version_id;
    }

    public void setVersion_id(String version_id) {
        this.version_id = version_id;
    }
}
