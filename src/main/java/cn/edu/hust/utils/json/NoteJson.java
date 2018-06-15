package cn.edu.hust.utils.json;

import cn.edu.hust.domain.Tags;
import cn.edu.hust.domain.Users;
import cn.edu.hust.domain.Versions;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NoteJson implements Serializable {
    private Timestamp created_at=new Timestamp(System.currentTimeMillis());
    private String id;
    private String notebook_id;
    private List<Tags> tags=new ArrayList<Tags>();
    private Timestamp updated_at=new Timestamp(System.currentTimeMillis());
    private List<Users> users=new ArrayList<Users>();
    private Versions version;
    private String version_id;

    public NoteJson() {
    }

    public void set(Timestamp created_at, String id, String notebook_id, List<Tags> tags, Timestamp updated_at, List<Users> users, Versions version, String version_id) {
        this.created_at = created_at;
        this.id = id;
        this.notebook_id = notebook_id;
        this.tags = tags;
        this.updated_at = updated_at;
        this.users = users;
        this.version = version;
        this.version_id = version_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
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

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public Versions getVersion() {
        return version;
    }

    public void setVersion(Versions version) {
        this.version = version;
    }

    public String getVersion_id() {
        return version_id;
    }

    public void setVersion_id(String version_id) {
        this.version_id = version_id;
    }
}
